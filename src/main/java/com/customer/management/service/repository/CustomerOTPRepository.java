package com.customer.management.service.repository;

import com.customer.management.service.entity.CustomerOTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOTPRepository extends JpaRepository<CustomerOTP, Long> {
}
