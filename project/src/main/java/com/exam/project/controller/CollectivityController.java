package com.exam.project.controller;

import com.exam.project.model.*;
import com.exam.project.service.FederationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/collectivities")
public class CollectivityController {

    private final FederationService service;

    public CollectivityController(FederationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAllCollectivities() {
        try {
            List<Collectivity> collectivities = service.getAllCollectivities();
            return ResponseEntity.ok(collectivities);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCollectivity(@PathVariable String id) {
        try {
            Collectivity collectivity = service.getCollectivityById(id);
            if (collectivity == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(collectivity);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<?> getCollectivityMembers(@PathVariable String id) {
        try {
            List<Member> members = service.getMembersByCollectivityId(id);
            return ResponseEntity.ok(members);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/financialAccounts")
    public ResponseEntity<?> getFinancialAccounts(
            @PathVariable String id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate at) {
        try {
            LocalDate dateQuery = (at != null) ? at : LocalDate.now();
            List<Account> accounts = service.getAccountsWithBalance(id, dateQuery);
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/membershipFees")
    public ResponseEntity<?> getMembershipFees(@PathVariable String id) {
        try {
            List<Contribution> fees = service.getMembershipFeesByCollectivityId(id);
            return ResponseEntity.ok(fees);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<?> getTransactions(
            @PathVariable String id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        try {
            List<Contribution> transactions = service.getTransactionsByCollectivityId(id, start, end);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}