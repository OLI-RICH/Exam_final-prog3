package com.exam.project.controller;

import com.exam.project.model.Account;
import com.exam.project.model.Collectivity;
import com.exam.project.model.Contribution;
import com.exam.project.model.Member;
import com.exam.project.service.CollectivityService;
import com.exam.project.service.MemberService;
import com.exam.project.service.FinancialService;
import com.exam.project.service.ContributionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/collectivities")
public class CollectivityController {
    private final CollectivityService collectivityService;
    private final MemberService memberService;
    private final FinancialService financialService;
    private final ContributionService contributionService;

    public CollectivityController(CollectivityService collectivityService, MemberService memberService, FinancialService financialService, ContributionService contributionService) {
        this.collectivityService = collectivityService;
        this.memberService = memberService;
        this.financialService = financialService;
        this.contributionService = contributionService;
    }

    @PostMapping
    public ResponseEntity<?> createCollectivity(@RequestBody Collectivity col) {
        try {
            col.setId("COL-" + UUID.randomUUID().toString().substring(0, 5));
            col.setCreationDate(LocalDate.now());
            collectivityService.createCollectivity(col);
            return ResponseEntity.ok(col);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCollectivities() {
        try {
            List<Collectivity> collectivities = collectivityService.getAllCollectivities();
            return ResponseEntity.ok(collectivities);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCollectivity(@PathVariable String id) {
        try {
            Collectivity collectivity = collectivityService.getCollectivityById(id);
            if (collectivity == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(collectivity);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<?> getCollectivityMembers(@PathVariable String id) {
        try {
            List<Member> members = memberService.getMembersByCollectivityId(id);
            return ResponseEntity.ok(members);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/financialAccounts")
    public ResponseEntity<?> getFinancialAccounts(@PathVariable String id, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate at) {
        try {
            LocalDate dateQuery = (at != null) ? at : LocalDate.now();
            List<Account> accounts = financialService.getAccountsWithBalance(id, dateQuery);
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/membershipFees")
    public ResponseEntity<?> getMembershipFees(@PathVariable String id) {
        try {
            List<Contribution> fees = contributionService.getMembershipFeesByCollectivityId(id);
            return ResponseEntity.ok(fees);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<?> getTransactions(@PathVariable String id, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        try {
            List<Contribution> transactions = contributionService.getTransactionsByCollectivityId(id, start, end);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/identity")
    public ResponseEntity<?> assignIdentity(@PathVariable String id, @RequestParam String number, @RequestParam String name) {
        try {
            collectivityService.assignIdentity(id, number, name);
            return ResponseEntity.ok("Identité assignée avec succès");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}