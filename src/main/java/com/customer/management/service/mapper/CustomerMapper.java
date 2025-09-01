package com.customer.management.service.mapper;

import com.customer.management.service.entity.CustomerModel;
import com.customer.management.service.entity.CustomerAddress;
import com.customer.management.service.request.CustomerRequest;
import com.customer.management.service.response.CustomerResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CustomerMapper is responsible for converting data between different layers:
 * - Converts CustomerRequest (incoming API payload) into CustomerModel (entity for persistence).
 * - Converts CustomerModel (database entity) into CustomerResponse (outgoing API response).
 * This ensures a clean separation between API layer, persistence layer, and business logic.
 */
@Component
public class CustomerMapper {

    /**
     * Converts a CustomerRequest object into a CustomerModel entity.
     * Steps performed:
     * - Maps all basic customer fields (first name, last name, email, etc.).
     * - Sets createdDate and updatedDate with the current timestamp.
     * - If addresses are provided, maps each address and links it back to the customer
     *   so that the relationship is properly established before persisting.
     *
     * @param request the incoming customer request object from API
     * @return a fully populated CustomerModel entity ready for persistence
     */
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
            List<CustomerAddress> list = request.getAddresses().stream().map(address -> {
                address.setCustomer(customer);
                return address;
            }).collect(Collectors.toList());
            customer.setAddress(list);
        }
        return customer;
    }

    /**
     * Converts a CustomerModel entity into a CustomerResponse object.
     *
     * Steps performed:
     * - Maps all fields from the entity, including personal details, status, timestamps, and addresses.
     * - Used for sending a clean and structured response to the client.
     *
     * @param CustomerModel the persisted customer entity from database
     * @return a CustomerResponse object containing customer data for API response
     */
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
