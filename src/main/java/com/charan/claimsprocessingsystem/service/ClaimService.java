package com.charan.claimsprocessingsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;  // For DI (why? Spring injects; if not, manual new – tight coupling).
import org.springframework.stereotype.Service;  // Marks as service (why? Spring bean – auto-managed; if not, no DI).

import com.charan.claimsprocessingsystem.entity.Claim;  // Import entity (why? Use Claim object; if not, can't reference).
import com.charan.claimsprocessingsystem.repository.ClaimRepository;  // Import repo (why? For DB calls; if not, no save/find).

@Service
public class ClaimService {
    @Autowired
    private ClaimRepository repository;  // Why? DI repo – call save/find; why should? Auto-wired; if not, manual init – errors.

    public Claim submitClaim(Claim claim) {  // Why? Submit method – entry for new claims.
        claim.setStatus("SUBMITTED");  // Why? Business rule – initial state; if not, default null – logic breaks.
        return repository.save(claim);  // Why? Save to DB; if not, no persistence – data lost.
    }

    public Claim validateClaim(Long id) {  // Why? Validate method – update based on rules.
        Claim claim = repository.findById(id).orElseThrow(() -> new RuntimeException("Claim not found"));  // Why? Find or error; why should? Handle missing – robust; if not, null pointer crash.
        if (claim.getAmount() > 0 && claim.getDescription() != null && !claim.getDescription().isEmpty()) {  // Why? Rule check; if not, no validation – invalid data saved.
            claim.setStatus("VALIDATED");
        } else {
            claim.setStatus("REJECTED");
        }
        return repository.save(claim);  // Why? Update in DB; if not, changes not persisted.
    }

    public List<Claim> getAllClaims() {  // Why? Get all – for listing.
        return repository.findAll();  // Why? Repo method – free; if not, manual query – code bloat.
    }

    public Claim getClaimById(Long id) {  // Why? Get one – for tracking.
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Claim not found"));
    }
}