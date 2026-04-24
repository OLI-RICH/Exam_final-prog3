package com.exam.project.controller;

import com.exam.project.model.*;
import com.exam.project.service.FederationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class FederationController {

    private final FederationService service;

    public FederationController(FederationService service) {
        this.service = service;
    }

    @PostMapping("/collectivities")
    public ResponseEntity<?> createCollectivity(@RequestBody com.exam.project.model.Collectivity col) {
        try {
            col.setId("COL-" + UUID.randomUUID().toString().substring(0, 5));
            col.setCreationDate(LocalDate.now());
            service.createCollectivity(col);
            return ResponseEntity.status(HttpStatus.CREATED).body(col);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/collectivities/{id}/identity")
    public ResponseEntity<?> assignIdentity(@PathVariable String id,
                                            @RequestParam String number,
                                            @RequestParam String name) {
        try {
            service.assignIdentity(id, number, name);
            return ResponseEntity.ok("Identity successfully assigned");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/members")
    public ResponseEntity<?> addMember(@RequestBody com.exam.project.model.Member m) {
        try {
            m.setId("MEM-" + UUID.randomUUID().toString().substring(0, 5));
            m.setJoinDate(LocalDate.now());
            service.addMember(m);
            return ResponseEntity.ok("Member added.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<?> getMember(@PathVariable String id) {
        try {
            com.exam.project.model.Member member = service.getMemberById(id);
            if (member == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(member);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/members/{id}/payments")
    public ResponseEntity<?> recordPayment(@PathVariable String id, @RequestBody Contribution payment) {
        try {
            payment.setId("PAY-" + UUID.randomUUID().toString().substring(0, 8));
            payment.setMemberId(id);
            if (payment.getDate() == null) payment.setDate(LocalDate.now());
            service.recordContribution(payment);
            return ResponseEntity.ok("Payment registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        try {
            account.setId("ACC-" + UUID.randomUUID().toString().substring(0, 8));
            if (account.getBalance() == null) account.setBalance(java.math.BigDecimal.ZERO);
            service.createAccount(account);
            return ResponseEntity.ok("Account created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getAccount(@PathVariable String id) {
        try {
            Account account = service.getAccountById(id);
            if (account == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/accounts/owner/{ownerId}")
    public ResponseEntity<?> getAccountsByOwner(@PathVariable String ownerId) {
        try {
            List<Account> accounts = service.getAccountsByOwner(ownerId);
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/contributions")
    public ResponseEntity<?> createContribution(@RequestBody Contribution request) {
        try {
            if (request.getId() == null) {
                request.setId("CON-" + UUID.randomUUID().toString().substring(0, 8));
            }
            if (request.getDate() == null) {
                request.setDate(LocalDate.now());
            }
            service.recordContribution(request);
            return ResponseEntity.ok("Contribution recorded successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}