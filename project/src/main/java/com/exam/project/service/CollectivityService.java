package com.exam.project.service;

import com.exam.project.model.Account;
import com.exam.project.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class CollectivityService {

    private final AccountRepository accountRepository;

    public CollectivityService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAccountsWithBalanceAt(String collectivityId, LocalDate at) {
        try {
            return accountRepository.findByOwnerId(collectivityId);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving accounts", e);
        }
    }
}