package com.Banking_Application.demo.controller;

import com.Banking_Application.demo.model.AccountHolder;
import com.Banking_Application.demo.repository.AccountHolderRepo;
import com.Banking_Application.demo.service.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.JsonNode;

import java.util.List;

@RestController
public class AccountHolderContoller {

    @Autowired
    private AccountHolderService accountHolderService;

//     CREATE OR UPDATE
    @PostMapping("/create")
    public JsonNode createAccount(@RequestBody JsonNode jsonNode){
        return accountHolderService.create(jsonNode);
    }

//    Get all details
    @GetMapping("/show")
    public List<AccountHolder> showAccountHolderDetails(){
        return accountHolderService.getAllData();
    }
//  Get BY ID
    @GetMapping("/getById/{id}")
    public JsonNode getAccountHolderById(@PathVariable long id){
        return accountHolderService.getById(id);
    }

//    Delete any accountHolder details
    @DeleteMapping("/delete/{id}")
    public JsonNode deleteAccountHolderById(@PathVariable long id){
        return accountHolderService.deleteAccount(id);
    }

//    Deposit Money to Bank Account
    @PostMapping("/deposit/{accountId}/{transactionId}/{money}")
    public JsonNode deposit(@PathVariable long accountId, @PathVariable long transactionId,@PathVariable long money){
        return accountHolderService.depositMoney(accountId,transactionId,money);
    }
}
