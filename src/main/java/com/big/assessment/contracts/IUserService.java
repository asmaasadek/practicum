package com.big.assessment.contracts;

import com.big.assessment.entities.User;
import com.big.assessment.entities.UserTransaction;
import com.big.assessment.models.AmountDTO;
import com.big.assessment.models.UserDTO;
import com.big.assessment.models.UserTransactionDTO;

import java.util.List;

public interface IUserService {

    User addUser(UserDTO userDTO);

    UserTransaction sendAmount(Integer userId, AmountDTO amountDTO);

    List<UserTransactionDTO> listUserTransactions(Integer userId);
}
