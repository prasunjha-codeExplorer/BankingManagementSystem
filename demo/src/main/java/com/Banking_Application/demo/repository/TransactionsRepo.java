package com.Banking_Application.demo.repository;

import com.Banking_Application.demo.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepo extends JpaRepository<Transactions,Long> {

}
