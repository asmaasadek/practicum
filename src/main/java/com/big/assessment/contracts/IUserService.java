package com.big.assessment.contracts;

import com.big.assessment.models.AmountDTO;
import com.big.assessment.models.UserDTO;
import com.big.assessment.utilities.Result;

public interface IUserService {

    Result addUser(UserDTO userDTO);

    void sendAmount(Integer userId, AmountDTO amountDTO);

    void listMyTransactions();


}
