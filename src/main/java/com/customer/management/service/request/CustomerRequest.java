package com.customer.management.service.request;

import com.customer.management.service.entity.CustomerAddress;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

/**
 * Request DTO for customer data.
 * Used in POST/PUT/PATCH APIs.
 * Customer ID (used for update/delete)
 * First name (required)
 * Last name (required)
 * Full name (required)
 * Age (must be >= 1)
 * Mobile number (required)
 * Email address (must be valid)
 * Password (min 6 characters)
 * Customer addresses (must not be empty)
 * Fields used for PATCH requests (updates)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerRequest {

    private Long customerId;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Age must be at least 1")
    private Integer age;

    @NotBlank(message = "Mobile number is required")
    private String mobileNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String emailAddress;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 chars")
    private String password;

    @NotEmpty(message = "Addresses cannot be empty")
    @Valid
    private List<CustomerAddress> addresses;

    private String newMobileNumber;
    private String newEmailAddress;
    private String newPassword;
    private String confirmPassword;
    private String otp;
}
