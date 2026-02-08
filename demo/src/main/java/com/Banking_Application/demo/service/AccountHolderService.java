package com.Banking_Application.demo.service;

import com.Banking_Application.demo.model.AccountHolder;
import com.Banking_Application.demo.model.Transactions;
import com.Banking_Application.demo.repository.AccountHolderRepo;
import com.Banking_Application.demo.repository.TransactionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AccountHolderService {

    @Autowired
    private AccountHolderRepo accountHolderRepo;
    @Autowired
    private TransactionsRepo transactionsRepo;

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

    public JsonNode getById(long id) {
        AccountHolder accountHolder;
        ObjectNode response = mapper.createObjectNode();
        Optional<AccountHolder> optional = accountHolderRepo.findById(id);
        if(optional.isEmpty()){
            response.put("status","error");
            response.put("message","Account Holder not found");

            return response;
        }
        accountHolder = optional.get();
        response.put("status","success");
        response.put("message","Account Fetched Successfully");
        response.put("Id",accountHolder.getAccountId());
        response.put("Name",accountHolder.getAccountHolderName());
        response.put("balance",accountHolder.getBalance());

        return response;
    }

    public JsonNode deleteAccount(long id) {
        ObjectNode response = mapper.createObjectNode();
        Optional<AccountHolder> optional = accountHolderRepo.findById(id);
        if(optional.isEmpty()){
            response.put("status","error");
            response.put("message","Account Holder not found");

            return response;
        }
        accountHolderRepo.deleteById(id);
        response.put("status","success");
        response.put("message","Account deleted successfully");

        return response;

    }

    public JsonNode depositMoney(long accountId,long transactionId, long money) {
        ObjectNode response = mapper.createObjectNode();
        Optional<AccountHolder> optional = accountHolderRepo.findById(accountId);
        Optional<Transactions> optionalTransactions = transactionsRepo.findById(transactionId);

        if(optional.isEmpty()){
            response.put("status","error");
            response.put("message","Account Holder not found");

            return response;
        }
        AccountHolder accountHolder = optional.get();
        Transactions transactions = new Transactions();
        if(optionalTransactions.isPresent()) {
            transactions = optionalTransactions.get();
            transactions.setAmount(transactions.getAmount() + money);
        }else{
            transactions.setTransactionType("DEPOSIT");
            transactions.setAccountId(accountId);
            transactions.setDate(LocalDate.now());
            transactions.setAmount(money);
        }

        long val = Integer.parseInt(accountHolder.getBalance()) + money;
        accountHolder.setBalance(Long.toString(val));

        accountHolderRepo.save(accountHolder);
        transactionsRepo.save(transactions);
        response.put("status","success");
        response.put("message","Account Balance deposited successfully");

        return response;
    }
}
