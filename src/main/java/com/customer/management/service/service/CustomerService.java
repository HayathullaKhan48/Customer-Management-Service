package com.customer.management.service.service;

import com.customer.management.service.request.CustomerRequest;
import com.customer.management.service.response.CustomerResponse;
import org.springframework.data.domain.Page;

/**
 * Defines customer-related operations.
 */
public interface CustomerService {

    /**
     * Create a new customer
     *
     * @param request CustomerRequest object containing customer details
     * @return CustomerResponse object with saved customer information
     */
    CustomerResponse createCustomer(CustomerRequest request);

    /**
     * Get all customers with pagination & sorting
     *
     * @param page page number (0-based)
     * @param size number of records per page
     * @param sortBy field name to sort by
     * @return Page containing CustomerResponse objects
     */
    Page<CustomerResponse> getAllCustomers(int page, int size, String sortBy);

    /**
     * Get customer by ID
     *
     * @param customerId unique ID of the customer
     * @return CustomerResponse object for the requested customer
     */
    CustomerResponse getCustomerById(Long customerId);

    /**
     * Update full customer details
     *
     * @param request CustomerRequest object with updated details
     * @return CustomerResponse object with updated customer information
     */
    CustomerResponse updateCustomer(CustomerRequest request);

    /**
     * Update only mobile number
     *
     * @param mobileNumber current mobile number of customer
     * @param newMobileNumber new mobile number to update
     * @return CustomerResponse object with updated mobile number
     */
    CustomerResponse updateMobileNumber(String mobileNumber, String newMobileNumber);

    /**
     * Update only email address
     *
     * @param mobileNumber mobile number of customer
     * @param newEmail new email address to update
     * @return CustomerResponse object with updated email address
     */
    CustomerResponse updateEmail(String mobileNumber, String newEmail);

    /**
     * Update password
     *
     * @param mobileNumber mobile number of customer
     * @param newPassword new password to set
     * @return CustomerResponse object with updated password
     */
    CustomerResponse updatePassword(String mobileNumber, String newPassword);

    /**
     * Reset password (with confirmation)
     *
     * @param mobileNumber mobile number of customer
     * @param newPassword new password to set
     * @param confirmPassword confirmation of the new password
     * @return CustomerResponse object with updated password
     */
    CustomerResponse forgetPassword(String mobileNumber, String newPassword, String confirmPassword);

    /**
     * Delete customer (soft delete, mark inactive)
     *
     * @param customerId unique ID of the customer
     * @return CustomerResponse object after marking customer inactive
     */
    CustomerResponse deleteCustomer(Long customerId);

    /**
     * Activate customer using OTP
     *
     * @param mobileNumber mobile number of customer
     * @param otp OTP code to validate and activate customer
     * @return CustomerResponse object after activation
     */
    CustomerResponse activateCustomerByOtp(String mobileNumber, String otp);
}
