package com.charan.claimsprocessingsystem.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "claims")
@Data
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String claimType;  // e.g., "health", "auto", "property"

    private String description;

    private Double amount;

    private String status = "SUBMITTED";  // SUBMITTED, VALIDATED, APPROVED, REJECTED

    private LocalDateTime submittedAt = LocalDateTime.now();
}