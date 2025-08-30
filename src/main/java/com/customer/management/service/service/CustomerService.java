package com.customer.management.service.service;

import com.customer.management.service.request.CustomerRequest;
import com.customer.management.service.response.CustomerResponse;
import org.springframework.data.domain.Page;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest request);

    Page<CustomerResponse> getAllCustomers(int page, int size, String sortBy);

    CustomerResponse getCustomerById(Long customerId);

    CustomerResponse updateCustomer(CustomerRequest request);

    CustomerResponse updateMobileNumber(String mobileNumber, String newMobileNumber);

    CustomerResponse updateEmail(String mobileNumber, String newEmail);

    CustomerResponse updatePassword(String mobileNumber, String newPassword);

    CustomerResponse forgetPassword(String mobileNumber, String newPassword, String confirmPassword);

    CustomerResponse deleteCustomer(Long customerId);

    CustomerResponse activateCustomerByOtp(String mobileNumber, String otp);
}
