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

/**
 * CustomerController is responsible for handling all incoming HTTP requests
 * related to customer operations (CRUD and custom actions like password reset).
 *  Why we need this class:
 * - Acts as the entry point for API clients (Postman, Frontend, Swagger)
 * - Delegates business logic to the CustomerService layer
 * - Handles validation, logging, and HTTP status responses
 */
@RestController
@RequestMapping("/customer-management-service/api/v1")
@RequiredArgsConstructor
public class CustomerController {

    // Service layer dependency to handle business logic
    private final CustomerService customerService;

    // Logger for tracking API calls and debugging
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    /**
     * Create a new customer.
     *
     * @param request the customer request body containing details like name, email, password, etc.
     * @return CustomerResponse with newly created customer details
     */
    @PostMapping("/create")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        log.info("Received createCustomer request for mobile: {}", request.getMobileNumber());
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    /**
     * Retrieve a paginated list of all customers.
     *
     * @param page   page number (default: 0)
     * @param size   number of records per page (default: 20)
     * @param sortBy field to sort by (default: createdDate)
     * @return Page<CustomerResponse> containing paginated results
     */
    @GetMapping("/customers")
    public ResponseEntity<Page<CustomerResponse>> allCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy) {

        log.info("Fetching all customers with page={}, size={}, sortBy={}", page, size, sortBy);
        Page<CustomerResponse> res = customerService.getAllCustomers(page, size, sortBy);
        return ResponseEntity.ok(res);
    }

    /**
     * Fetch a single customer by their unique ID.
     *
     * @param customerId the customer ID to search for
     * @return CustomerResponse containing customer details if found
     */
    @GetMapping("/getById/{customerId}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable Long customerId) {
        log.info("Fetching customer by ID: {}", customerId);
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    /**
     * Update a customer's mobile number.
     *
     * @param mobileNumber    current mobile number
     * @param newMobileNumber new mobile number to be updated
     * @return updated CustomerResponse
     */
    @PatchMapping("/updateMobile/{mobileNumber}")
    public ResponseEntity<CustomerResponse> updateMobile(@PathVariable @NotBlank String mobileNumber, @RequestParam @NotBlank String newMobileNumber) {
        log.info("Updating mobile number for {} to {}", mobileNumber, newMobileNumber);
        return ResponseEntity.ok(customerService.updateMobileNumber(mobileNumber, newMobileNumber));
    }

    /**
     * Update a customer's email address.
     *
     * @param mobileNumber current mobile number (used to identify the customer)
     * @param newEmail     new email address to be updated
     * @return updated CustomerResponse
     */
    @PatchMapping("/updateEmail/{mobileNumber}")
    public ResponseEntity<CustomerResponse> updateEmail(@PathVariable @NotBlank String mobileNumber, @RequestParam @Email String newEmail) {
        log.info("Updating email for {} to {}", mobileNumber, newEmail);
        return ResponseEntity.ok(customerService.updateEmail(mobileNumber, newEmail));
    }

    /**
     * Fully update customer details (like name, email, age, etc.).
     * This is a PUT request meaning the full customer record is replaced with new data.
     *
     * @param request updated customer details
     * @return updated CustomerResponse
     */
    @PutMapping("/customersUpdate")
    public ResponseEntity<CustomerResponse> updateFull(@Valid @RequestBody CustomerRequest request) {
        log.info("Updating full customer details for mobile: {}", request.getMobileNumber());
        return ResponseEntity.ok(customerService.updateCustomer(request));
    }

    /**
     * Change a customer's password manually.
     *
     * @param mobileNumber customer's mobile number
     * @param newPassword  new password to be set
     * @return updated CustomerResponse
     */
    @PatchMapping("/customer/changePassword/{mobileNumber}/{newPassword}")
    public ResponseEntity<CustomerResponse> changePassword(@PathVariable @NotBlank String mobileNumber,
                                                           @PathVariable @NotBlank String newPassword) {
        log.info("Changing password for customer: {}", mobileNumber);
        return ResponseEntity.ok(customerService.updatePassword(mobileNumber, newPassword));
    }

    /**
     * Forgot password flow (usually triggered by OTP verification).
     * Ensures that new password and confirm password match before update.
     *
     * @param mobileNumber     customer's mobile number
     * @param newPassword      new password
     * @param confirmPassword  confirm password (must match)
     * @return updated CustomerResponse
     */
    @PatchMapping("/forgotPassword/{mobileNumber}/{newPassword}/{confirmPassword}")
    public ResponseEntity<CustomerResponse> forgotPassword(@PathVariable @NotBlank String mobileNumber,
                                                           @PathVariable @NotBlank String newPassword,
                                                           @PathVariable @NotBlank String confirmPassword) {
        log.info("Processing forgot password for {}", mobileNumber);
        return ResponseEntity.ok(customerService.forgetPassword(mobileNumber, newPassword, confirmPassword));
    }

    /**
     * Soft delete a customer by marking status as INACTIVE.
     * Data is not physically removed from the database.
     *
     * @param customerId ID of the customer to delete
     * @return updated CustomerResponse with status set to INACTIVE
     */
    @DeleteMapping("/deleteCustomer/{customerId}")
    public ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable Long customerId) {
        log.info("Deleting customer with ID: {}", customerId);
        return ResponseEntity.ok(customerService.deleteCustomer(customerId));
    }

    /**
     * Activate a customer account using OTP verification.
     *
     * @param mobileNumber customer's mobile number
     * @param otpNo        OTP provided by the customer
     * @return updated CustomerResponse with status set to ACTIVE if OTP is valid
     */
    @PatchMapping("/activateByOtp/{mobileNumber}/{otpNo}")
    public ResponseEntity<CustomerResponse> activateByOtp(@PathVariable @NotBlank String mobileNumber,
                                                          @PathVariable @NotBlank String otpNo) {
        log.info("Activating customer {} with OTP {}", mobileNumber, otpNo);
        return ResponseEntity.ok(customerService.activateCustomerByOtp(mobileNumber, otpNo));
    }
}
