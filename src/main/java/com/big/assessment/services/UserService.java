package com.big.assessment.services;

import com.big.assessment.contracts.IUserService;
import com.big.assessment.entities.User;
import com.big.assessment.entities.UserTransaction;
import com.big.assessment.exceptions.UserBadRequestException;
import com.big.assessment.models.AmountDTO;
import com.big.assessment.models.UserDTO;
import com.big.assessment.models.UserTransactionDTO;
import com.big.assessment.repositories.UserRepository;
import com.big.assessment.repositories.UserTransactionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTransactionRepository userTransactionRepository;


    @Override
    public User addUser(UserDTO userDTO) {
        log.info("Add new User...");
        if (userRepository.findByUserMail(userDTO.getUserMail()) != null)
            throw new UserBadRequestException(HttpStatus.FOUND,
                    "UserMail should be unique");

        return userRepository.save(new User().fromUserDTO(userDTO));
    }

    @Override
    public UserTransaction sendAmount(Integer userId, AmountDTO amountDTO) {
        log.info("Sending VC from user: " + userId + " to user: " + amountDTO.getToUserId());
        Optional<User> fromUser =
                userRepository.findById(userId);
        if (!fromUser.isPresent())
            throw new UserBadRequestException(HttpStatus.UNAUTHORIZED,
                    "Not authorized UserId: " + userId);

        User sender = fromUser.get();
        if (sender.getVirtualCurrency() < amountDTO.getAmount())
            throw new UserBadRequestException(HttpStatus.EXPECTATION_FAILED,
                    "This amount is greater than your limit!: ");

        //Update Sender VC
        sender.setVirtualCurrency(sender.getVirtualCurrency() - amountDTO.getAmount());
        userRepository.save(sender);

        //Update Receiver VC
        userRepository.updateUserVC(amountDTO.getToUserId(), amountDTO.getAmount());

        //Save Transaction
        return userTransactionRepository.save(new UserTransaction().registerTransaction(sender.getId(),
                amountDTO.getToUserId(),
                amountDTO.getAmount()));
    }

    @Override
    public List<UserTransactionDTO> listUserTransactions(Integer userId) {
        log.info("Listing transactions for userId: " + userId);
        Optional<User> fromUser =
                userRepository.findById(userId);
        if (!fromUser.isPresent())
            throw new UserBadRequestException(HttpStatus.UNAUTHORIZED,
                    "Not authorized UserId: " + userId);

        List<UserTransaction> userTransactions =
                userTransactionRepository.findAllByFromUserOrToUser(userId, userId);

        if (userTransactions != null && userTransactions.size() < 1)
            throw new UserBadRequestException(HttpStatus.NOT_FOUND,
                    "Not active UserId: " + userId + ", There are no transactions for this user!");

        List<UserTransactionDTO> result = new ArrayList<>();
        for (UserTransaction transaction :
                userTransactions) {
            result.add(new UserTransactionDTO().fromUserTransaction(transaction, userId));
        }
        return result;
    }

}
