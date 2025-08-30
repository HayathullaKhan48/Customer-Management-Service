package com.customer.management.service.controller;

import com.customer.management.service.request.CustomerRequest;
import com.customer.management.service.response.CustomerResponse;
import com.customer.management.service.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer-management-service/api/v1")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    @PostMapping("/create")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        log.info("Received createCustomer request for mobile: {}", request.getMobileNumber());
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    @GetMapping("/customers")
    public ResponseEntity<Page<CustomerResponse>> allCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy) {

        log.info("Fetching all customers with page={}, size={}, sortBy={}", page, size, sortBy);
        Page<CustomerResponse> res = customerService.getAllCustomers(page, size, sortBy);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable Long customerId) {
        log.info("Fetching customer by ID: {}", customerId);
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    @PatchMapping("/customerUpdate/mobile/{mobileNumber}")
    public ResponseEntity<CustomerResponse> updateMobile(
            @PathVariable @NotBlank String mobileNumber,
            @RequestParam @NotBlank String newMobileNumber) {
        log.info("Updating mobile number for {} to {}", mobileNumber, newMobileNumber);
        return ResponseEntity.ok(customerService.updateMobileNumber(mobileNumber, newMobileNumber));
    }

    @PatchMapping("/customerUpdate/email/{mobileNumber}")
    public ResponseEntity<CustomerResponse> updateEmail(
            @PathVariable @NotBlank String mobileNumber,
            @RequestParam @Email String newEmail) {
        log.info("Updating email for {} to {}", mobileNumber, newEmail);
        return ResponseEntity.ok(customerService.updateEmail(mobileNumber, newEmail));
    }

    @PutMapping("/customersUpdate")
    public ResponseEntity<CustomerResponse> updateFull(@Valid @RequestBody CustomerRequest request) {
        log.info("Updating full customer details for mobile: {}", request.getMobileNumber());
        return ResponseEntity.ok(customerService.updateCustomer(request));
    }

    @PatchMapping("/customer/changePassword/{mobileNumber}/{newPassword}")
    public ResponseEntity<CustomerResponse> changePassword(
            @PathVariable @NotBlank String mobileNumber,
            @PathVariable @NotBlank String newPassword) {
        log.info("Changing password for customer: {}", mobileNumber);
        return ResponseEntity.ok(customerService.updatePassword(mobileNumber, newPassword));
    }

    @PatchMapping("/customerForgotPassword/{mobileNumber}/{newPassword}/{confirmPassword}")
    public ResponseEntity<CustomerResponse> forgotPassword(
            @PathVariable @NotBlank String mobileNumber,
            @PathVariable @NotBlank String newPassword,
            @PathVariable @NotBlank String confirmPassword) {
        log.info("Processing forgot password for {}", mobileNumber);
        return ResponseEntity.ok(customerService.forgetPassword(mobileNumber, newPassword, confirmPassword));
    }

    @DeleteMapping("/customerDelete/{customerId}")
    public ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable Long customerId) {
        log.info("Deleting customer with ID: {}", customerId);
        return ResponseEntity.ok(customerService.deleteCustomer(customerId));
    }

    @PatchMapping("/activateByOtp/{mobileNumber}/{otpNo}")
    public ResponseEntity<CustomerResponse> activateByOtp(
            @PathVariable @NotBlank String mobileNumber,
            @PathVariable @NotBlank String otpNo) {
        log.info("Activating customer {} with OTP {}", mobileNumber, otpNo);
        return ResponseEntity.ok(customerService.activateCustomerByOtp(mobileNumber, otpNo));
    }
}
