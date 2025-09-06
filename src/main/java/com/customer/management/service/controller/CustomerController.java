package com.customer.management.service.controller;

import com.customer.management.service.request.CustomerRequest;
import com.customer.management.service.response.CustomerResponse;
import com.customer.management.service.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * CustomerController is responsible for handling all incoming HTTP requests
 * related to customer operations (CRUD and custom actions like password reset).
 * Why we need this class:
 * - Acts as the entry point for API clients (Postman, Frontend, Swagger)
 * - Delegates business logic to the CustomerService layer
 * - Handles validation, logging, and HTTP status responses
 */
@RestController
@RequestMapping("/customer-management-service/api/v1")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    /**
     * Create a new customer in the system.
     *
     * @param request CustomerRequest containing all required details like name, email, password, and addresses
     * @return ResponseEntity containing CustomerResponse with newly created customer details
     */
    @PostMapping("/create")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        logger.info("Received createCustomer request for mobile: {}", request.getMobileNumber());
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    /**
     * Retrieve a paginated list of all customers with optional sorting.
     *
     * @param page   page number (default: 0)
     * @param size   number of records per page (default: 20)
     * @param sortBy field to sort results by (default: createdDate)
     * @return ResponseEntity containing a paginated Page of CustomerResponse objects
     */
    @GetMapping("/customers")
    public ResponseEntity<Page<CustomerResponse>> getCustomers(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "20") int size,
                                                               @RequestParam(defaultValue = "createdDate") String sortBy) {

        logger.info("Fetching all customers with page={}, size={}, sortBy={}", page, size, sortBy);
        Page<CustomerResponse> request = customerService.getCustomers(page, size, sortBy);
        return ResponseEntity.ok(request);
    }


    /**
     * Get a customer by their mobile number.
     *
     * @param mobileNumber mobile number of the customer
     * @return ResponseEntity containing CustomerResponse with matched customer details
     */
    @GetMapping("/getCustomerByMobileNumber/{mobileNumber}")
    public ResponseEntity<CustomerResponse> getByMobileNumber(@PathVariable String mobileNumber) {
        return ResponseEntity.ok(customerService.getCustomerByMobileNumber(mobileNumber));
    }

    /**
     * Get a customer by their email address.
     *
     * @param emailAddress email address of the customer
     * @return ResponseEntity containing CustomerResponse with matched customer details
     */
    @GetMapping("/getCustomerByEmailAddress/{emailAddress}")
    public ResponseEntity<CustomerResponse> getByEmailAddress(@PathVariable String emailAddress) {
        return ResponseEntity.ok(customerService.getCustomerByEmailAddress(emailAddress));
    }

    /**
     * Get a customer by their full name.
     *
     * @param fullName full name of the customer
     * @return ResponseEntity containing CustomerResponse with matched customer details
     */
    @GetMapping("/getByFullName/{fullName}")
    public ResponseEntity<CustomerResponse> getByFullName(@PathVariable String fullName) {
        return ResponseEntity.ok(customerService.getCustomerByFullName(fullName));
    }

    /**
     * Partially update a customer's mobile number.
     *
     * @param customerId      ID of the customer whose mobile number is to be updated
     * @param newMobileNumber new mobile number to set
     * @return ResponseEntity containing CustomerResponse with updated customer details
     */
    @PatchMapping("/updateCustomerByMobileNumber/{customerId}/{newMobileNumber}")
    public ResponseEntity<CustomerResponse> updateCustomerByMobileNumber(@PathVariable Long customerId, @PathVariable String newMobileNumber) {
        return ResponseEntity.ok(customerService.updateCustomerByMobileNumber(customerId, newMobileNumber));
    }

    /**
     * Partially update a customer's email address.
     *
     * @param customerId      ID of the customer whose email address is to be updated
     * @param newEmailAddress new email address to set
     * @return ResponseEntity containing CustomerResponse with updated customer details
     */
    @PatchMapping("/updateCustomerByEmailAddress/{customerId}/{newEmailAddress}")
    public ResponseEntity<CustomerResponse> updateCustomerByEmailAddress(@PathVariable Long customerId, @PathVariable String newEmailAddress){
        return ResponseEntity.ok(customerService.updateCustomerByEmailAddress(customerId, newEmailAddress));
    }

    /**
     * Soft-delete a customer by their mobile number (mark as inactive).
     *
     * @param mobileNumber mobile number of the customer to be deleted
     * @return ResponseEntity containing CustomerResponse with updated (inactive) status
     */
    @DeleteMapping("/deleteCustomerByMobileNumber/{mobileNumber}")
    public ResponseEntity<CustomerResponse> deleteCustomerByMobileNumber(@PathVariable String mobileNumber){
        return ResponseEntity.ok(customerService.deleteCustomerByMobileNumber(mobileNumber));
    }

    /**
     * Soft-delete a customer by their email address (mark as inactive).
     *
     * @param emailAddress email address of the customer to be deleted
     * @return ResponseEntity containing CustomerResponse with updated (inactive) status
     */
    @DeleteMapping("/deleteCustomerByEmailAddress/{emailAddress}")
    public ResponseEntity<CustomerResponse> deleteCustomerByEmailAddress(@PathVariable String emailAddress){
        return ResponseEntity.ok(customerService.deleteCustomerByEmailAddress(emailAddress));
    }

    /**
     * Update customer password using their mobile number.
     *
     * @param mobileNumber mobile number of the customer
     * @param newPassword  new password to set
     * @return ResponseEntity containing CustomerResponse with updated password information
     */
    @PatchMapping("/updatePasswordByMobileNumber/{mobileNumber}/{newPassword}")
    public ResponseEntity<CustomerResponse> updatePasswordByMobileNumber(@PathVariable String mobileNumber, @PathVariable String newPassword){
        return ResponseEntity.ok(customerService.updatePasswordByMobileNumber(mobileNumber, newPassword));
    }

    /**
     * Update customer password using their email address.
     *
     * @param emailAddress email address of the customer
     * @param newPassword  new password to set
     * @return ResponseEntity containing CustomerResponse with updated password information
     */
    @PatchMapping("/updatePasswordByEmailAddress/{emailAddress}/{newPassword}")
    public ResponseEntity<CustomerResponse> updatePasswordByEmailAddress(@PathVariable String emailAddress, @PathVariable String newPassword){
        return ResponseEntity.ok(customerService.updatePasswordByEmailAddress(emailAddress, newPassword));
    }

    @PatchMapping("/updateCustomerMobileNumberByCustomerId/{customerId}/{newMobileNumber}")
    public ResponseEntity<String> updateCustomerMobileNumberByCustomerId(@PathVariable Long customerId, @PathVariable String newMobileNumber) {
        return ResponseEntity.ok(customerService.updateCustomerMobileNumberByCustomerId(customerId, newMobileNumber));
    }

    @PatchMapping("/updateCustomerEmailAddressByCustomerId/{customerId}/{newEmailAddress}")
    public ResponseEntity<String> updateCustomerEmailAddressByCustomerId(@PathVariable Long customerId, @PathVariable String newEmailAddress){
        return ResponseEntity.ok(customerService.updateCustomerEmailAddressByCustomerId(customerId, newEmailAddress));
    }

    @PatchMapping("/updateCustomerPasswordByCustomerId/{customerId}/{newPassword}")
    public ResponseEntity<String> updateCustomerPasswordByCustomerId(@PathVariable Long customerId, @PathVariable String newPassword){
        return ResponseEntity.ok(customerService.updatePasswordByCustomerId(customerId, newPassword));
    }

    @DeleteMapping("/deleteCustomerByCustomerId/{customerId}")
    public ResponseEntity<String> deleteCustomerByCustomerId(@PathVariable Long customerId){
        return ResponseEntity.ok(customerService.deleteCustomerByCustomerId(customerId));
    }

}
