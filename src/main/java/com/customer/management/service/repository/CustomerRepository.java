package com.customer.management.service.repository;

import com.customer.management.service.entity.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {
    Optional<CustomerModel> findByCustomerId(Long customerId);
    Optional<CustomerModel> findByMobileNumber(String mobileNumber);
    boolean existsByMobileNumber(String mobileNumber);
    boolean existsByEmailAddress(String emailAddress);
    boolean existsByFullName(String fullName);
}
