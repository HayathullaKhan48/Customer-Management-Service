package com.customer.management.service.service;

import com.customer.management.service.request.CustomerRequest;
import com.customer.management.service.response.CustomerResponse;
import org.springframework.data.domain.Page;


/**
 * CustomerService defines all customer-related operations.
 *
 * Why we need this interface:
 * - Provides a clear contract for customer-related operations.
 * - Helps in separating the service implementation from its usage.
 * - Improves testability (we can mock this interface in unit tests).
 * - Makes code modular and maintainable.
 */
public interface CustomerService {

    /**
     * Creates a new customer and persists it to the database.
     *
     * @param request CustomerRequest object containing customer details
     *                (name, email, password, age, addresses).
     * @return CustomerResponse containing saved customer information
     *         including ID, timestamps, and status.
     */
    CustomerResponse createCustomer(CustomerRequest request);

    /**
     * Retrieves all customers in a paginated and sorted format.
     *
     * @param page   page number (0-based, default is 0)
     * @param size   number of records per page (default is 20)
     * @param sortBy field name to sort by (e.g. createdDate, fullName)
     * @return Page of CustomerResponse objects for the requested page
     */
    Page<CustomerResponse> getCustomers(int page, int size, String sortBy);

    /**
     * Retrieves a single customer by their mobile number.
     *
     * @param mobileNumber the unique mobile number of the customer
     * @return CustomerResponse containing matching customer details
     */
    CustomerResponse getCustomerByMobileNumber(String mobileNumber);

    /**
     * Retrieves a single customer by their email address.
     *
     * @param emailAddress the unique email address of the customer
     * @return CustomerResponse containing matching customer details
     */
    CustomerResponse getCustomerByEmailAddress(String emailAddress);

    /**
     * Retrieves a single customer by their full name.
     *
     * @param fullName the full name of the customer
     * @return CustomerResponse containing matching customer details
     */
    CustomerResponse getCustomerByFullName(String fullName);

    /**
     * Updates customer's mobile number.
     *
     * @param customerId     unique identifier of the customer
     * @param newMobileNumber new mobile number to update
     * @return CustomerResponse with updated customer details
     */
    CustomerResponse updateCustomerByMobileNumber(Long customerId, String newMobileNumber);

    /**
     * Updates customer's email address.
     *
     * @param customerId      unique identifier of the customer
     * @param newEmailAddress new email address to update
     * @return CustomerResponse with updated customer details
     */
    CustomerResponse updateCustomerByEmailAddress(Long customerId, String newEmailAddress);

    /**
     * Soft deletes a customer by marking them as INACTIVE
     * using their mobile number.
     *
     * @param mobileNumber unique mobile number of the customer
     * @return CustomerResponse with status set to INACTIVE
     */
    CustomerResponse deleteCustomerByMobileNumber(String mobileNumber);

    /**
     * Soft deletes a customer by marking them as INACTIVE
     * using their email address.
     *
     * @param emailAddress unique email address of the customer
     * @return CustomerResponse with status set to INACTIVE
     */
    CustomerResponse deleteCustomerByEmailAddress(String emailAddress);

    /**
     * Updates customer's password using their mobile number.
     *
     * @param mobileNumber mobile number of the customer
     * @param newPassword  new password to be saved (will be encrypted)
     * @return CustomerResponse with updated password (not exposed in response)
     */
    CustomerResponse updatePasswordByMobileNumber(String mobileNumber, String newPassword);

    /**
     * Updates customer's password using their email address.
     *
     * @param emailAddress email address of the customer
     * @param newPassword  new password to be saved (will be encrypted)
     * @return CustomerResponse with updated password (not exposed in response)
     */
    CustomerResponse updatePasswordByEmailAddress(String emailAddress, String newPassword);

    /**
     * Updates a customer's mobile number using their customer ID.
     *
     * @param customerId     unique identifier of the customer
     * @param newMobileNumber new mobile number
     * @return CustomerResponse containing updated customer details
     */
    String updateCustomerMobileNumberByCustomerId(Long customerId, String newMobileNumber);

    /**
     * Updates a customer's email address using their customer ID.
     *
     * @param customerId      unique identifier of the customer
     * @param newEmailAddress new email address
     * @return CustomerResponse containing updated customer details
     */
    String updateCustomerEmailAddressByCustomerId(Long customerId, String newEmailAddress);

    /**
     * Updates a customer's password using their customer ID.
     *
     * @param customerId  unique identifier of the customer
     * @param newPassword new password to be encrypted and saved
     * @return CustomerResponse; password itself is not exposed
     */
    String updatePasswordByCustomerId(Long customerId, String newPassword);

    /**
     * Softly deletes a customer using their customer ID by marking them as INACTIVE.
     *
     * @param customerId unique identifier of the customer
     * @return CustomerResponse reflecting updated status as INACTIVE
     */
    String deleteCustomerByCustomerId(Long customerId);
}
