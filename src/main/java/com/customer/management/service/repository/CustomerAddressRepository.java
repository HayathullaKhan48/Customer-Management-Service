package com.customer.management.service.repository;

import com.customer.management.service.entity.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for CustomerAddress entity.
 * Provides basic CRUD operations using JPA.
 */
public interface CustomerAddressRepository extends JpaRepository<AddressModel, Long> {
    @Modifying
    @Query("DELETE FROM AddressModel a WHERE a.customer.customerId = :customerId")
    void deleteAllByCustomerId(@Param("customerId") Long customerId);

}
