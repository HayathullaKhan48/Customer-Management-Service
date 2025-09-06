package com.customer.management.service.repository;

import com.customer.management.service.entity.OtpModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for OtpModel entity.
 * Handles storing and retrieving OTP records.
 */
public interface CustomerOTPRepository extends JpaRepository<OtpModel, Long> {
    @Modifying
    @Query("DELETE FROM OtpModel o WHERE o.customer.customerId = :customerId")
    void deleteAllByCustomerId(@Param("customerId") Long customerId);

}
