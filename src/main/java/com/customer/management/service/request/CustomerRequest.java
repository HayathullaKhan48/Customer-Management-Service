package com.customer.management.service.request;

import com.customer.management.service.entity.CustomerAddress;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
