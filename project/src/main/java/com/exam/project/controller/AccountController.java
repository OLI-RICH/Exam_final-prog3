package com.exam.project.controller;

import com.exam.project.dto.CreateAccount;
import com.exam.project.model.Account;
import com.exam.project.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable String id) {
        try {
            // Utilisation de .map() pour gérer l'Optional retourné par le Service
            return accountService.getAccount(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur : " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody CreateAccount request) {
        try {
            Account account = new Account();
            account.setId("ACC-" + UUID.randomUUID().toString().substring(0, 8));
            account.setType(request.getType());
            account.setOwnerId(request.getOwnerId());
            account.setBalance(request.getInitialBalance() != null ? request.getInitialBalance() : java.math.BigDecimal.ZERO);

            
            account.setHolderName(request.getHolderName());
            account.setBankName(request.getBankName());
            account.setAccountNumber(request.getAccountNumber());
            account.setMobileService(request.getMobileService());
            account.setPhoneNumber(request.getPhoneNumber());

            accountService.createAccount(account);
            return ResponseEntity.ok("Compte de trésorerie créé avec succès.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur de création : " + e.getMessage());
        }
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<?> getAccountsByOwner(@PathVariable String ownerId) {
        try {
            List<Account> accounts = accountService.getAccountsByOwner(ownerId);
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}