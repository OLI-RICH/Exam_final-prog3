package com.exam.project.controller;

import com.exam.project.model.Account;
import com.exam.project.service.CollectivityService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/collectivities")
public class CollectivityController {

    private final CollectivityService collectivityService;

    public CollectivityController(CollectivityService collectivityService) {
        this.collectivityService = collectivityService;
    }

    @GetMapping("/{id}/financialAccounts")
    public ResponseEntity<List<Account>> getFinancialAccounts(
            @PathVariable String id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate at) {

        LocalDate referenceDate = (at != null) ? at : LocalDate.now();
        List<Account> accounts = collectivityService.getAccountsWithBalanceAt(id, referenceDate);
        return ResponseEntity.ok(accounts);
    }
}