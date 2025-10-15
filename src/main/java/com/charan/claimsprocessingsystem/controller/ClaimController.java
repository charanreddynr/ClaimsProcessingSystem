package com.charan.claimsprocessingsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;  // DI service.
import org.springframework.http.ResponseEntity;  // HTTP responses (why? Status codes like 200 OK).
// API annotations.
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charan.claimsprocessingsystem.entity.Claim;  // Entity for data.
import com.charan.claimsprocessingsystem.service.ClaimService;  // Service for logic.

@RestController  // Why? REST controller – returns JSON; if not, plain controller – no JSON.
@RequestMapping("/api/claims")  // Why? Base URL – all endpoints under /api/claims; if not, separate mappings – messy.
public class ClaimController {
    @Autowired
    private ClaimService service;  // Why? DI service – call methods; if not, manual – coupling.

    @PostMapping("/submit")  // Why? POST for create; if GET, wrong method – security issues.
    public ResponseEntity<Claim> submit(@RequestBody Claim claim) {  // @RequestBody: JSON to Claim.
        return ResponseEntity.ok(service.submitClaim(claim));  // Why? OK response; if not, no status – bad API.
    }

    @PutMapping("/validate/{id}")  // Why? PUT for update; {id} for variable.
    public ResponseEntity<Claim> validate(@PathVariable Long id) {  // @PathVariable: URL param to Long.
        return ResponseEntity.ok(service.validateClaim(id));
    }

    @GetMapping  // Why? GET for read all.
    public List<Claim> getAll() {
        return service.getAllClaims();  // Returns JSON list.
    }

    @GetMapping("/{id}")  // Why? GET for read one.
    public ResponseEntity<Claim> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getClaimById(id));
    }
}