package com.big.assessment.controller;

import com.big.assessment.contracts.IUserService;
import com.big.assessment.entities.User;
import com.big.assessment.entities.UserTransaction;
import com.big.assessment.exceptions.UserBadRequestException;
import com.big.assessment.models.AmountDTO;
import com.big.assessment.models.UserDTO;
import com.big.assessment.models.UserTransactionDTO;
import com.big.assessment.utilities.ErrorResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok(userService.addUser(userDTO));
        } catch (UserBadRequestException ex) {
            return new ResponseEntity(new ErrorResult(ex.getErrorCode(), ex.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    @PostMapping("send-vc")
    public ResponseEntity<UserTransaction> sendAmount(@RequestHeader("user_id") Integer userId,
                                                      @RequestBody AmountDTO amountDTO) {
        try {
            return ResponseEntity.ok(userService.sendAmount(userId, amountDTO));
        } catch (UserBadRequestException ex) {
            return new ResponseEntity(new ErrorResult(ex.getErrorCode(), ex.getMessage()),
                    ex.getErrorCode());
        }
    }

    @GetMapping("list-transactions")
    public ResponseEntity<List<UserTransactionDTO>> listUserTransaction(@RequestHeader("user_id") Integer userId) {
        try {
            return ResponseEntity.ok(userService.listUserTransactions(userId));
        } catch (UserBadRequestException ex) {
            return new ResponseEntity(new ErrorResult(ex.getErrorCode(), ex.getMessage()),
                    ex.getErrorCode());
        }

    }
}
