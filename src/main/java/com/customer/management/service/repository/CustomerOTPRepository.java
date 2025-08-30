package com.customer.management.service.repository;

import com.customer.management.service.entity.CustomerOTP;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for CustomerOTP entity.
 * Handles storing and retrieving OTP records.
 */
public interface CustomerOTPRepository extends JpaRepository<CustomerOTP, Long> {
}
