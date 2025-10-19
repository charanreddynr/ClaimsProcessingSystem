package com.charan.claimsprocessingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.charan.claimsprocessingsystem.entity.Claim;
import com.charan.claimsprocessingsystem.integration.FraudDetectionService;
import com.charan.claimsprocessingsystem.integration.NotificationService;
import com.charan.claimsprocessingsystem.repository.ClaimRepository;
import com.charan.claimsprocessingsystem.service.ClaimService;

public class ClaimServiceTest {
    @Mock // Fake repo – test service without real DB.
    private ClaimRepository repository;

    @Mock // Fake fraud – control output.
    private FraudDetectionService fraudService;

    @Mock // Added: Fake notification to avoid NPE in valid case.
    private NotificationService notificationService;

    @InjectMocks // Inject all mocks into service.
    private ClaimService service;

    @BeforeEach
    public void setup() { // Init mocks before each test.
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSubmitClaim() { // Test submit – set status, save.
        Claim claim = new Claim();
        claim.setClaimType("health");
        claim.setAmount(5000.0);
        when(repository.save(any(Claim.class))).thenReturn(claim); // Mock save – return input.

        Claim result = service.submitClaim(claim);
        assertEquals("SUBMITTED", result.getStatus()); // Assert – check expected.
    }

    @Test
    public void testValidateClaim_Valid() { // Test valid case – "VALIDATED".
        Claim claim = new Claim();
        claim.setId(1L);
        claim.setAmount(5000.0);
        claim.setDescription("Test");
        when(repository.findById(1L)).thenReturn(Optional.of(claim)); // Mock find.
        when(fraudService.isFraudulent(5000.0)).thenReturn(false); // Mock no fraud.
        when(repository.save(any(Claim.class))).thenReturn(claim); // Mock save.

        Claim result = service.validateClaim(1L);
        assertEquals("VALIDATED", result.getStatus());
    }

    @Test
    public void testValidateClaim_Fraud() { // Test fraud – "REJECTED".
        Claim claim = new Claim();
        claim.setId(1L);
        claim.setAmount(12000.0);
        claim.setDescription("Test");
        when(repository.findById(1L)).thenReturn(Optional.of(claim));
        when(fraudService.isFraudulent(12000.0)).thenReturn(true); // Mock fraud.
        when(repository.save(any(Claim.class))).thenReturn(claim);

        Claim result = service.validateClaim(1L);
        assertEquals("REJECTED", result.getStatus());
    }
}