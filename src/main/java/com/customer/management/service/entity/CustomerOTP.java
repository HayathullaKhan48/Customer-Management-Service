package com.customer.management.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * CustomerOTP Entity:
 * - Represents the OTP (One-Time Password) details linked to a customer.
 * - Each record stores:
 *   - A unique OTP ID (Primary Key)
 *   - The actual OTP value (6 digits)
 *   - The date and time when the OTP was generated
 *   - The customer to whom this OTP belongs (Many-to-One relationship)
 * Used for verifying customer identity during login, registration, or password reset.
 */
@Entity
@Table(name = "customer_otp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerOTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otp_id")
    private Long otpId;

    @Column(name = "otp_value", nullable = false, length = 6)
    private String otpValue;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerModel customer;
}
