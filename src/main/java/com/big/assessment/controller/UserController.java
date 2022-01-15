package com.big.assessment.controller;

import com.big.assessment.contracts.IUserService;
import com.big.assessment.models.AmountDTO;
import com.big.assessment.models.UserDTO;
import com.big.assessment.models.UserTransactionDTO;
import com.big.assessment.utilities.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping
    @ResponseBody
    public Result<Integer> addUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @PostMapping("send-vc")
    @ResponseBody
    public void sendAmount(@RequestHeader("user_id") Integer userId,
                           @RequestBody AmountDTO amountDTO) {
        userService.sendAmount(userId, amountDTO);
    }

    @GetMapping("list-transactions")
    @ResponseBody
    public List<UserTransactionDTO> listUserTransaction(@RequestHeader("user_id") Integer userId) {
        return userService.listUserTransactions(userId);
    }
}
