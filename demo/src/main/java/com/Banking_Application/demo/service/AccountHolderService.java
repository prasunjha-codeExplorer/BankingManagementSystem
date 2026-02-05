package com.Banking_Application.demo.service;

import com.Banking_Application.demo.model.AccountHolder;
import com.Banking_Application.demo.repository.AccountHolderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.Optional;

@Service
public class AccountHolderService {

    @Autowired
    private AccountHolderRepo accountHolderRepo;

    final ObjectMapper mapper = new ObjectMapper();
    public JsonNode create(JsonNode jsonNode) {

        ObjectNode response = mapper.createObjectNode();
        AccountHolder accountHolder;

        // UPDATION
        if(jsonNode.has("accountId")){
            Long accountId = jsonNode.get("accountId").asLong();

            Optional<AccountHolder> optional = accountHolderRepo.findById(accountId);

            if(optional.isPresent()){
                accountHolder = optional.get();
            }else{
                response.put("status","error");
                response.put("message","Account Holder not found");
                return response;
            }
        }else{
            // CREATION
            accountHolder = new AccountHolder();
        }

        if(jsonNode.has("accountHolderName")){
        accountHolder.setAccountHolderName(jsonNode.get("accountHolderName").asText());}
        else{accountHolder.setAccountHolderName("null");}

        if(jsonNode.has("balance")){
        accountHolder.setBalance(jsonNode.get("balance").asText());}
        else{accountHolder.setBalance("null");}

        accountHolderRepo.save(accountHolder);
        response.put("status","success");
        response.put("message","Account Holder registered successfully");
        return response;
    }

    public List<AccountHolder> getAllData() {
        ObjectNode response = mapper.createObjectNode();
        List<AccountHolder> accountHolderList = accountHolderRepo.findAll();

        if(accountHolderList.isEmpty()){
            response.put("status","error");
            response.put("message","No Data is present");

            return accountHolderList;
        }

        response.put("status","success");
        return accountHolderList;
    }
}
