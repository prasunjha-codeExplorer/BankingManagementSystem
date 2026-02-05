package com.Banking_Application.demo.controller;

import com.Banking_Application.demo.model.AccountHolder;
import com.Banking_Application.demo.repository.AccountHolderRepo;
import com.Banking_Application.demo.service.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
}
