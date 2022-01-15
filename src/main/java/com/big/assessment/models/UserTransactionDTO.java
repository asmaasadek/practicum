package com.big.assessment.models;

import com.big.assessment.entities.UserTransaction;
import com.big.assessment.utilities.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTransactionDTO implements Serializable {
    Date transactionDate;
    Double transactionAmount;
    UserType userType;

    public UserTransactionDTO fromUserTransaction(UserTransaction userTransaction, Integer userId) {
        this.setTransactionDate(userTransaction.getTransactionDate());
        this.setTransactionAmount(userTransaction.getTransactionAmount());
        if (userTransaction.getFromUser() == userId)
            this.setUserType(UserType.SENDER);
        else
            this.setUserType(UserType.RECEIVER);
        return this;
    }
}
