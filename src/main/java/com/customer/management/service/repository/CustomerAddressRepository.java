package com.customer.management.service.repository;

import com.customer.management.service.entity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for CustomerAddress entity.
 * Provides basic CRUD operations using JPA.
 */
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {
}
