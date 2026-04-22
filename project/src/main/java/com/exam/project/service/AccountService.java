package com.exam.project.service;

import com.exam.project.model.Account;
import com.exam.project.model.AccountType;
import com.exam.project.repository.AccountRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Cette méthode est celle que le Controller ne trouve pas
    public Optional<Account> getAccount(String id) throws SQLException {
        return accountRepository.findById(id);
    }

    public void createAccount(Account account) throws SQLException {
        if (account.getType() == AccountType.CASH && accountRepository.hasCashAccount(account.getOwnerId())) {
            throw new IllegalArgumentException("Une caisse existe déjà pour cette collectivité.");
        }

        if (account.getType() == AccountType.BANK && account.getAccountNumber() != null) {
            if (account.getAccountNumber().length() != 23 || !account.getAccountNumber().matches("\\d{23}")) {
                throw new IllegalArgumentException("Le RIB doit contenir exactement 23 chiffres.");
            }
        }
        accountRepository.save(account);
    }

    public List<Account> getAccountsByOwner(String ownerId) throws SQLException {
        return accountRepository.findByOwnerId(ownerId);
    }

    public void updateBalance(String accountId, BigDecimal newBalance) throws SQLException {
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le solde ne peut pas être négatif.");
        }
        accountRepository.updateBalance(accountId, newBalance);
    }
}