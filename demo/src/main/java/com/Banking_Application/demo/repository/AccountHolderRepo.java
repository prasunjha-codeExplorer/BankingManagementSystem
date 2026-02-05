package com.Banking_Application.demo.repository;

import com.Banking_Application.demo.model.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHolderRepo extends JpaRepository<AccountHolder,Long> {
}
