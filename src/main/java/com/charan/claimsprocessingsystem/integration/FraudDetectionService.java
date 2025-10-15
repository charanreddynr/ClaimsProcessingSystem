package com.charan.claimsprocessingsystem.integration;

import org.springframework.stereotype.Service;

@Service  // Why? Marks as bean – Spring scans/injects.
public class FraudDetectionService {
    public boolean isFraudulent(double amount) {  // Why? Check rule – stub.
        return amount > 10000;  // Why? Simple logic; expand to real API later.
    }
}