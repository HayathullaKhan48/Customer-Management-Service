package com.customer.management.service.service.impl;

import com.customer.management.service.entity.CustomerModel;
import com.customer.management.service.entity.CustomerOTP;
import com.customer.management.service.enums.CustomerStatus;
import com.customer.management.service.exceptions.CustomerAlreadyExistsException;
import com.customer.management.service.exceptions.CustomerNotFoundException;
import com.customer.management.service.exceptions.InvalidOtpException;
import com.customer.management.service.mapper.CustomerMapper;
import com.customer.management.service.repository.CustomerOTPRepository;
import com.customer.management.service.repository.CustomerRepository;
import com.customer.management.service.request.CustomerRequest;
import com.customer.management.service.response.CustomerResponse;
import com.customer.management.service.service.CustomerService;
import com.customer.management.service.util.OtpUtil;
import com.customer.management.service.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerRepository customerRepository;
    private final CustomerOTPRepository otpRepository;
    private final CustomerMapper mapper;
    private final PasswordUtil passwordUtil;
    private final OtpUtil otpUtil;

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
        CustomerModel model = mapper.toCustomerModel(request);
        model.setPassword(passwordUtil.encode(request.getPassword()));
        model.setStatus(CustomerStatus.INACTIVE);
        model.setCreatedDate(LocalDateTime.now());
        model.setUpdatedDate(LocalDateTime.now());
        if (model.getAddress() != null && !model.getAddress().isEmpty()) {
            model.getAddress().forEach(a -> a.setCustomer(model));
        }
        CustomerModel saved = customerRepository.save(model);
        String code = otpUtil.generateOtp();
        CustomerOTP otp = CustomerOTP.builder()
                .otpValue(code)
                .createdDate(LocalDateTime.now())
                .customer(saved)
                .build();
        otpRepository.save(otp);

        logger.info("Customer created (id={} mobile={}) with OTP {}", saved.getCustomerId(), saved.getMobileNumber(), code);

        return mapper.toCustomerResponse(saved);
    }

    @Override
    public Page<CustomerResponse> getAllCustomers(int page, int size, String sortBy) {
        logger.info("Fetching customers page={} size={} sortBy={}", page, size, sortBy);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<CustomerModel> pageData = customerRepository.findAll(pageable);
        return pageData.map(mapper::toCustomerResponse);
    }

    @Override
    public CustomerResponse getCustomerById(Long customerId) {
        CustomerModel c = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
        return mapper.toCustomerResponse(c);
    }

    @Override
    public CustomerResponse updateCustomer(CustomerRequest request) {
        CustomerModel model = customerRepository.findByMobileNumber(request.getMobileNumber())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with mobile: " + request.getMobileNumber()));

        model.setFirstName(request.getFirstName());
        model.setLastName(request.getLastName());
        model.setFullName(request.getFullName());
        model.setAge(request.getAge());
        model.setEmailAddress(request.getEmailAddress());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            model.setPassword(passwordUtil.encode(request.getPassword()));
        }
        model.setUpdatedDate(LocalDateTime.now());
        if (request.getAddresses() != null) {
            model.getAddress().clear();
            request.getAddresses().forEach(addr -> {
                addr.setCustomer(model);
                model.getAddress().add(addr);
            });
        }
        CustomerModel updated = customerRepository.save(model);
        logger.info("Customer updated: {}", model.getMobileNumber());
        return mapper.toCustomerResponse(updated);
    }

    @Override
    public CustomerResponse updateMobileNumber(String mobileNumber, String newMobileNumber) {
        CustomerModel model = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with mobile: " + mobileNumber));
        if (customerRepository.existsByMobileNumber(newMobileNumber)) {
            throw new CustomerAlreadyExistsException("New mobile number already exists");
        }
        model.setMobileNumber(newMobileNumber);
        model.setUpdatedDate(LocalDateTime.now());
        CustomerModel saved = customerRepository.save(model);
        logger.info("Mobile updated for customerId={}", saved.getCustomerId());
        return mapper.toCustomerResponse(saved);
    }

    @Override
    public CustomerResponse updateEmail(String mobileNumber, String newEmail) {
        CustomerModel model = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with mobile: " + mobileNumber));
        if (customerRepository.existsByEmailAddress(newEmail)) {
            throw new CustomerAlreadyExistsException("New email already exists");
        }
        model.setEmailAddress(newEmail);
        model.setUpdatedDate(LocalDateTime.now());
        CustomerModel saved = customerRepository.save(model);
        logger.info("Email updated for customerId={}", saved.getCustomerId());
        return mapper.toCustomerResponse(saved);
    }

    @Override
    public CustomerResponse updatePassword(String mobileNumber, String newPassword) {
        CustomerModel model = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        model.setPassword(passwordUtil.encode(newPassword));
        model.setUpdatedDate(LocalDateTime.now());
        CustomerModel saved = customerRepository.save(model);
        logger.info("Password changed for customerId={}", saved.getCustomerId());
        return mapper.toCustomerResponse(saved);
    }

    @Override
    public CustomerResponse forgetPassword(String mobileNumber, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("Password and confirm password do not match");
        }
        return updatePassword(mobileNumber, newPassword);
    }

    @Override
    public CustomerResponse deleteCustomer(Long customerId) {
        CustomerModel model = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        model.setStatus(CustomerStatus.INACTIVE);
        model.setUpdatedDate(LocalDateTime.now());
        CustomerModel saved = customerRepository.save(model);
        logger.info("Customer soft-deleted (INACTIVE) id={}", customerId);
        return mapper.toCustomerResponse(saved);
    }

    @Override
    public CustomerResponse activateCustomerByOtp(String mobileNumber, String otp) {
        CustomerModel customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        boolean ok = customer.getOtp().stream()
                .anyMatch(OTP -> OTP.getOtpValue().equals(otp));
        if (!ok) {
            throw new InvalidOtpException("Invalid OTP");
        }
        customer.setStatus(CustomerStatus.ACTIVE);
        customer.setUpdatedDate(LocalDateTime.now());
        CustomerModel saved = customerRepository.save(customer);
        logger.info("Customer activated id={} mobile={}", saved.getCustomerId(), saved.getMobileNumber());
        return mapper.toCustomerResponse(saved);
    }
}
