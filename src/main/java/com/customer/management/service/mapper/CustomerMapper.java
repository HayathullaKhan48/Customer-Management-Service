package com.customer.management.service.mapper;

import com.customer.management.service.entity.CustomerModel;
import com.customer.management.service.entity.CustomerAddress;
import com.customer.management.service.request.CustomerRequest;
import com.customer.management.service.response.CustomerResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    public CustomerModel toCustomerModel(CustomerRequest request) {
        CustomerModel customer = CustomerModel.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .fullName(request.getFullName())
                .age(request.getAge())
                .mobileNumber(request.getMobileNumber())
                .emailAddress(request.getEmailAddress())
                .password(request.getPassword())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
        if (request.getAddresses() != null) {
            List<CustomerAddress> list = request.getAddresses().stream().map(addr -> {
                addr.setCustomer(customer);
                return addr;
            }).collect(Collectors.toList());
            customer.setAddress(list);
        }
        return customer;
    }

    public CustomerResponse toCustomerResponse(CustomerModel CustomerModel) {
        return CustomerResponse.builder()
                .customerId(CustomerModel.getCustomerId())
                .firstName(CustomerModel.getFirstName())
                .lastName(CustomerModel.getLastName())
                .fullName(CustomerModel.getFullName())
                .age(CustomerModel.getAge())
                .mobileNumber(CustomerModel.getMobileNumber())
                .emailAddress(CustomerModel.getEmailAddress())
                .status(CustomerModel.getStatus())
                .createdDate(CustomerModel.getCreatedDate())
                .updatedDate(CustomerModel.getUpdatedDate())
                .addresses(CustomerModel.getAddress())
                .build();
    }
}
