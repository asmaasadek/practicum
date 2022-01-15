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
import com.big.assessment.utilities.Result;
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
    public Result addUser(UserDTO userDTO) {
        Result result = new Result();
        try {
            validateAddingUser(userDTO);

            result.setEntity(userRepository.save(new User().fromUserDTO(userDTO)).getId());
            result.setCode(HttpStatus.OK.value());
            result.setMessage("User Added Successfully!");

        } catch (UserBadRequestException ex) {
            ex.printStackTrace();
            result.setCode(ex.getErrorCode().value());
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    @Override
    public void sendAmount(Integer userId, AmountDTO amountDTO) {
        Optional<User> fromUser =
                userRepository.findById(userId);
        if (!fromUser.isPresent())
            throw new UserBadRequestException(HttpStatus.UNAUTHORIZED,
                    "Not authorized UserId: " + userId);

        User sender = fromUser.get();
        if (sender.getVirtualCurrency() < amountDTO.getAmount())
            throw new UserBadRequestException(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE,
                    "This amount is greater than your limit!: " + userId);

        sender.setVirtualCurrency(sender.getVirtualCurrency() - amountDTO.getAmount());
        userRepository.save(sender);

        userRepository.updateUserVC(amountDTO.getToUserId(), amountDTO.getAmount());

        saveTransaction(sender.getId(), amountDTO.getToUserId());
    }

    @Override
    public List<UserTransactionDTO> listUserTransactions(Integer userId) {
        Optional<User> fromUser =
                userRepository.findById(userId);
        if (!fromUser.isPresent())
            throw new UserBadRequestException(HttpStatus.UNAUTHORIZED,
                    "Not authorized UserId: " + userId);

        List<UserTransaction> userTransactions =
                userTransactionRepository.findAllByFromUserOrToUser(userId, userId);

        List<UserTransactionDTO> result = new ArrayList<>();
        for (UserTransaction transaction :
                userTransactions) {
            result.add(new UserTransactionDTO().fromUserTransaction(transaction, userId));
        }
        return result;
    }

    private void validateAddingUser(UserDTO userDTO) {
        if (userRepository.findByUserMail(userDTO.getUserMail()) != null) {
            throw new UserBadRequestException(HttpStatus.FOUND,
                    "UserMail should be unique");
        }
    }

    private void saveTransaction(Integer fromUserId, Integer toUserId) {
        try {
            userTransactionRepository.save(new UserTransaction()
                    .registerTransaction(fromUserId, toUserId));
        } catch (Exception ex) {

        }
    }
}
