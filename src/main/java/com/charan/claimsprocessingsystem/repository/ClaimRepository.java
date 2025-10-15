package com.charan.claimsprocessingsystem.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.charan.claimsprocessingsystem.entity.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
}