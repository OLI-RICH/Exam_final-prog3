package com.exam.project.controller;

import com.exam.project.service.FederationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/members")
public class MemberController {
    private final FederationService federationService;

    public MemberController(FederationService federationService) {
        this.federationService = federationService;
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> createBulk() {
        return ResponseEntity.ok("Gestion administrative des membres.");
    }
}