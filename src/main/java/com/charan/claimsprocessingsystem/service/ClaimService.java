package com.charan.claimsprocessingsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charan.claimsprocessingsystem.entity.Claim;
import com.charan.claimsprocessingsystem.integration.FraudDetectionService;
import com.charan.claimsprocessingsystem.integration.NotificationService;
import com.charan.claimsprocessingsystem.repository.ClaimRepository;
@Service
public class ClaimService {
    @Autowired
    private ClaimRepository repository;
    @Autowired  // Added – fixes NPE.
    private FraudDetectionService fraudService;
 // Add field
    @Autowired
    private NotificationService notificationService;
    public Claim submitClaim(Claim claim) {
        claim.setStatus("SUBMITTED");
        return repository.save(claim);
    }

    public Claim validateClaim(Long id) {
        Claim claim = repository.findById(id).orElseThrow(() -> new RuntimeException("Claim not found"));
        if (claim.getAmount() > 0 && claim.getDescription() != null && !claim.getDescription().isEmpty() && !fraudService.isFraudulent(claim.getAmount())) {
            claim.setStatus("VALIDATED");
            notificationService.sendEmail("user@email.com", "Claim Validated", "ID " + id + " validated."); // Why? Notify.
            notificationService.sendSms("1234567890", "Claim ID " + id + " validated.");
        } else {
            claim.setStatus("REJECTED");
        }
        return repository.save(claim);
    }

    public List<Claim> getAllClaims() {
        return repository.findAll();
    }

    public Claim getClaimById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Claim not found"));
    }
}