package com.customer.management.service.service.impl;

import com.customer.management.service.entity.AddressModel;
import com.customer.management.service.entity.CustomerModel;
import com.customer.management.service.entity.OtpModel;
import com.customer.management.service.enums.CustomerStatus;
import com.customer.management.service.exceptions.CustomerAlreadyExistsException;
import com.customer.management.service.exceptions.CustomerNotFoundException;
import com.customer.management.service.mapper.CustomerMapper;
import com.customer.management.service.repository.CustomerAddressRepository;
import com.customer.management.service.repository.CustomerOTPRepository;
import com.customer.management.service.repository.CustomerRepository;
import com.customer.management.service.request.CustomerRequest;
import com.customer.management.service.response.CustomerResponse;
import com.customer.management.service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.customer.management.service.mapper.CustomerMapper.*;

/**
 * Implementation of {@link CustomerService}.
 * Handles all business logic related to customers:
 * - Creating customers and generating OTPs
 * - Retrieving customers by various identifiers (mobile, email, fullName)
 * - Updating customer details (mobile, email, password)
 * - Deleting customers (soft-delete or physical delete)
 * Annotated with {@link Transactional} to ensure that all database operations
 * within a method execute as a single transaction.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerRepository customerRepository;
    private final CustomerAddressRepository addressRepository;
    private final CustomerOTPRepository otpRepository;

    /**
     * Creates a new customer, saves related addresses, and generates an OTP.
     *
     * @param request Customer details from API request
     * @return {@link CustomerResponse} containing saved customer details,
     *         addresses, and OTP value
     * @throws CustomerAlreadyExistsException if mobile number, email, or full name already exist
     */
    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        logger.info("Create request received for mobile: {}", request.getMobileNumber());

        if (customerRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new CustomerAlreadyExistsException("Mobile number already exists");
        }
        if (customerRepository.existsByEmailAddress(request.getEmailAddress())) {
            throw new CustomerAlreadyExistsException("Email already exists");
        }
        if (customerRepository.existsByFullName(request.getFullName())) {
            throw new CustomerAlreadyExistsException("Full name already exists");
        }

        CustomerModel model = toCustomerModel(request);
        CustomerModel savedModel = customerRepository.save(model);
        logger.info("addresses {} ", request.getAddresses());
        List<AddressModel> addressModels = new ArrayList<>();
        request.getAddresses()
                .forEach(addressRequest -> addressModels.add(
                        addressRepository.saveAndFlush(requestToAddressMapper(savedModel, addressRequest))));

        OtpModel optModel = otpRepository.saveAndFlush(requestToOtpMapper(savedModel));

        logger.info("Customer created (id={} mobile={})", savedModel.getCustomerId(), savedModel.getMobileNumber());
        return toCustomerResponse(savedModel, addressModels, optModel);
    }

    /**
     * Retrieves all customers with pagination and sorting.
     *
     * @param page   Page number (0-based index)
     * @param size   Number of records per page
     * @param sortBy Field to sort results by (descending order)
     * @return Page of {@link CustomerResponse} objects
     */
    @Override
    public Page<CustomerResponse> getCustomers(int page, int size, String sortBy) {
        logger.info("Fetching customers page={} size={} sortBy={}", page, size, sortBy);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<CustomerModel> pageData = customerRepository.findAll(pageable);
        return pageData.map(CustomerMapper::toCustomerResponse);
    }

    /**
     * Retrieves a customer by their mobile number.
     *
     * @param mobileNumber Unique mobile number of the customer
     * @return {@link CustomerResponse} containing customer details
     * @throws CustomerNotFoundException if no customer is found with the given mobile number
     */
    @Override
    public CustomerResponse getCustomerByMobileNumber(String mobileNumber) {
        CustomerModel model = customerRepository.findCustomerByMobileNumber(mobileNumber)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not found with Mobile Number: "+ mobileNumber));
        return CustomerMapper.toCustomerResponse(model);
    }

    /**
     * Retrieves a customer by their email address.
     *
     * @param emailAddress Unique email address of the customer
     * @return {@link CustomerResponse} containing customer details
     * @throws CustomerNotFoundException if no customer is found with the given email address
     */
    @Override
    public CustomerResponse getCustomerByEmailAddress(String emailAddress) {
        CustomerModel model = customerRepository.findCustomerByEmailAddress(emailAddress)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not found with Email Address: "+ emailAddress));
        return CustomerMapper.toCustomerResponse(model);
    }

    /**
     * Retrieves a customer by their full name.
     *
     * @param fullName Full name of the customer
     * @return {@link CustomerResponse} containing customer details
     * @throws CustomerNotFoundException if no customer is found with the given full name
     */
    @Override
    public CustomerResponse getCustomerByFullName(String fullName) {
        CustomerModel model = customerRepository.findCustomerByFullName(fullName)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not found with fullName: "+ fullName));
        return CustomerMapper.toCustomerResponse(model);
    }

    /**
     * Updates the customer's mobile number.
     *
     * @param customerId      Unique ID of the customer
     * @param newMobileNumber New mobile number to update
     * @return {@link CustomerResponse} with updated mobile number
     * @throws CustomerNotFoundException if no customer is found with the given ID
     */
    @Override
    public CustomerResponse updateCustomerByMobileNumber(Long customerId, String newMobileNumber) {
        CustomerModel model = customerRepository.findByCustomerId(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not found with customerId: "+ customerId));
        model.setMobileNumber(newMobileNumber);
        CustomerModel updatedCustomerMobileNumber = customerRepository.saveAndFlush(model);
        return CustomerMapper.toCustomerResponse(updatedCustomerMobileNumber);
    }

    /**
     * Updates the customer's email address.
     *
     * @param customerId      Unique ID of the customer
     * @param newEmailAddress New email address to update
     * @return {@link CustomerResponse} with updated email address
     * @throws CustomerNotFoundException if no customer is found with the given ID
     */
    @Override
    public CustomerResponse updateCustomerByEmailAddress(Long customerId, String newEmailAddress) {
        CustomerModel model = customerRepository.findByCustomerId(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not found with customerId: "+ customerId));
        model.setEmailAddress(newEmailAddress);
        CustomerModel updatedCustomerEmail = customerRepository.saveAndFlush(model);
        return CustomerMapper.toCustomerResponse(updatedCustomerEmail);
    }

    /**
     * Deletes a customer using their mobile number.
     *
     * @param mobileNumber Unique mobile number of the customer
     * @return {@link CustomerResponse} of deleted customer
     * @throws CustomerNotFoundException if no customer is found with the given mobile number
     */
    @Override
    public CustomerResponse deleteCustomerByMobileNumber(String mobileNumber) {
        CustomerModel model = customerRepository.findCustomerByMobileNumber(mobileNumber)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not Found with Mobile Number: "+ mobileNumber));
        customerRepository.delete(model);
        return CustomerMapper.toCustomerResponse(model);
    }

    /**
     * Deletes a customer using their email address.
     *
     * @param emailAddress Unique email address of the customer
     * @return {@link CustomerResponse} of deleted customer
     * @throws CustomerNotFoundException if no customer is found with the given email address
     */
    @Override
    public CustomerResponse deleteCustomerByEmailAddress(String emailAddress) {
        CustomerModel model = customerRepository.findCustomerByEmailAddress(emailAddress)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not Found with Email Address: "+ emailAddress));
        customerRepository.delete(model);
        return CustomerMapper.toCustomerResponse(model);
    }

    /**
     * Updates a customer's password using their mobile number.
     *
     * @param mobileNumber Customer's mobile number
     * @param newPassword  New password (will be encrypted before saving)
     * @return {@link CustomerResponse} with updated password (not exposed in response)
     * @throws CustomerNotFoundException if no customer is found with the given mobile number
     */
    @Override
    public CustomerResponse updatePasswordByMobileNumber(String mobileNumber, String newPassword) {
        CustomerModel model = customerRepository.findCustomerByMobileNumber(mobileNumber)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not Found with Mobile Number: "+ mobileNumber));
        model.setPassword(newPassword);
        CustomerModel updatedCustomer = customerRepository.saveAndFlush(model);
        return CustomerMapper.toCustomerResponse(updatedCustomer);
    }

    /**
     * Updates a customer's password using their email address.
     *
     * @param emailAddress Customer's email address
     * @param newPassword  New password (will be encrypted before saving)
     * @return {@link CustomerResponse} with updated password (not exposed in response)
     * @throws CustomerNotFoundException if no customer is found with the given email address
     */
    @Override
    public CustomerResponse updatePasswordByEmailAddress(String emailAddress, String newPassword) {
        CustomerModel model = customerRepository.findCustomerByEmailAddress(emailAddress)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not Found with Email Address: "+ emailAddress));
        model.setPassword(newPassword);
        CustomerModel updatedCustomer = customerRepository.saveAndFlush(model);
        return CustomerMapper.toCustomerResponse(updatedCustomer);
    }

    /**
     * Updates the mobile number of a customer by their customer ID.
     *
     * @param customerId      Unique identifier of the customer
     * @param newMobileNumber New mobile number to update
     * @return Success message
     * @throws RuntimeException if the customer with the given ID does not exist
     */
    @Override
    @Transactional
    public String updateCustomerMobileNumberByCustomerId(Long customerId, String newMobileNumber) {
        CustomerModel customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with customerId: " + customerId));
        customer.setMobileNumber(newMobileNumber);
        customerRepository.save(customer);
        return "Mobile number updated successfully";
    }

    /**
     * Updates the email address of a customer by their customer ID.
     *
     * @param customerId      Unique identifier of the customer
     * @param newEmailAddress New email address to update
     * @return Success message
     * @throws RuntimeException if the customer with the given ID does not exist
     */
    @Override
    @Transactional
    public String updateCustomerEmailAddressByCustomerId(Long customerId, String newEmailAddress) {
        CustomerModel customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with customerId: " + customerId));
        customer.setEmailAddress(newEmailAddress);
        customerRepository.save(customer);
        return "Email address updated successfully";
    }

    /**
     * Updates the password of a customer by their customer ID.
     *
     * @param customerId Unique identifier of the customer
     * @param newPassword New password to update
     * @return Success message
     * @throws RuntimeException if the customer with the given ID does not exist
     */
    @Override
    @Transactional
    public String updatePasswordByCustomerId(Long customerId, String newPassword) {
        CustomerModel customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with customerId: " + customerId));
        customer.setPassword(newPassword);
        customerRepository.save(customer);
        return "Password updated successfully";
    }

    /**
     * Deletes a customer by marking them as INACTIVE using their customer ID.
     *
     * @param customerId Unique identifier of the customer
     * @return Success message
     * @throws RuntimeException if the customer with the given ID does not exist
     */
    @Override
    @Transactional
    public String deleteCustomerByCustomerId(Long customerId) {
        CustomerModel customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with customerId: " + customerId));
        customerRepository.save(customer);
        return "Customer deleted successfully";
    }

}
