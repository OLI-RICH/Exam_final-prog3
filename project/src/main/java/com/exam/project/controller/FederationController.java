package com.exam.project.controller;

import com.exam.project.service.FederationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FederationController {

    private final FederationService federationService;

    public FederationController(FederationService federationService) {
        this.federationService = federationService;
    }

    @PatchMapping("/collectivities/{id}/identity")
    public ResponseEntity<?> assignIdentity(@PathVariable String id, @RequestBody Map<String, String> body) {
        try {
            String number = body.get("identificationNumber");
            String name = body.get("uniqueName");
            federationService.assignCollectivityIdentity(id, number, name);
            return ResponseEntity.ok("Identity assigned successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}