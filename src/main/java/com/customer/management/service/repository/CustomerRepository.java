package com.customer.management.service.repository;

import com.customer.management.service.entity.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for CustomerModel entity.
 * Contains custom find and existence checks.
 * Find customer by ID
 * Find customer by mobile number
 * Check if mobile number already exists
 * Check if email address already exists
 * Check if full name already exists
 */
public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {
    Optional<CustomerModel> findByCustomerId(Long customerId);
    Optional<CustomerModel> findByMobileNumber(String mobileNumber);
    boolean existsByMobileNumber(String mobileNumber);
    boolean existsByEmailAddress(String emailAddress);
    boolean existsByFullName(String fullName);
}
