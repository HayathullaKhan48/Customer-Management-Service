package com.customer.management.service.mapper;

import com.customer.management.service.entity.AddressModel;
import com.customer.management.service.entity.CustomerModel;
import com.customer.management.service.entity.OtpModel;
import com.customer.management.service.enums.CustomerStatus;
import com.customer.management.service.request.AddressRequest;
import com.customer.management.service.request.CustomerRequest;
import com.customer.management.service.response.AddressResponse;
import com.customer.management.service.response.CustomerResponse;

import java.util.ArrayList;
import java.util.List;

import static com.customer.management.service.util.OtpUtil.generateOtp;
import static com.customer.management.service.util.PasswordUtil.autoGenerateHashPassword;

/**
 * CustomerMapper handles all object mapping between:
 * - Request objects (CustomerRequest, AddressRequest) coming from API layer
 * - Entity objects (CustomerModel, AddressModel, OtpModel) used in persistence layer
 * - Response objects (CustomerResponse, AddressResponse) sent back to the client
 * This approach ensures clean separation of concerns by keeping
 * conversion logic isolated from service/business logic.
 */

public class CustomerMapper {

    /**
     * Converts a {@link CustomerRequest} into a {@link CustomerModel} entity.
     * <p>
     * Steps performed:
     * <ul>
     *     <li>Maps all basic customer fields (first name, last name, email, etc.)</li>
     *     <li>Auto-generates a secure hashed password</li>
     *     <li>Sets customer status as {@link CustomerStatus#INACTIVE} by default</li>
     *     <li>Initializes createdDate and updatedDate automatically (via entity lifecycle)</li>
     * </ul>
     *
     * @param request the incoming customer data from API
     * @return a fully populated {@link CustomerModel} ready for persistence
     */
    public static CustomerModel toCustomerModel(CustomerRequest request) {
        return CustomerModel.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .fullName(request.getFullName())
                .age(request.getAge())
                .password(autoGenerateHashPassword())
                .mobileNumber(request.getMobileNumber())
                .emailAddress(request.getEmailAddress())
                .status(CustomerStatus.INACTIVE)
                .build();
    }

    /**
     * Maps an {@link AddressRequest} object to an {@link AddressModel} entity
     * and links it with the given customer.
     *
     * @param model   the parent {@link CustomerModel} entity to which this address belongs
     * @param address the address request coming from API
     * @return a fully populated {@link AddressModel} linked to the customer
     */
    public static AddressModel requestToAddressMapper(CustomerModel model, AddressRequest address) {
       return AddressModel.builder()
               .street(address.getStreet())
               .city(address.getCity())
               .state(address.getState())
               .country(address.getCountry())
               .addressType(address.getAddressType())
               .pincode(address.getPincode())
               .customer(model)
               .build();
    }

    /**
     * Prepares a new {@link OtpModel} entity for a customer.
     * <p>
     * Steps:
     * <ul>
     *     <li>Generates a secure OTP using {@link com.customer.management.service.util.OtpUtil}</li>
     *     <li>Associates OTP with the given customer</li>
     * </ul>
     *
     * @param customerModel the customer for whom OTP is being generated
     * @return a new {@link OtpModel} entity ready to be persisted
     */
    public static OtpModel requestToOtpMapper(CustomerModel customerModel) {
        String otpValue = generateOtp();
        return OtpModel.builder()
                .otpValue(otpValue)
                .customer(customerModel)
                .build();
    }

    /**
     * Converts a {@link CustomerModel} along with its addresses and OTP
     * into a {@link CustomerResponse} object for API response.
     *
     * @param customerModel the persisted customer entity from database
     * @param models        list of address entities linked with the customer
     * @param otpModel      the generated OTP entity
     * @return a {@link CustomerResponse} object containing complete customer details
     */
    public static CustomerResponse toCustomerResponse(CustomerModel customerModel, List<AddressModel> models, OtpModel otpModel) {
        return CustomerResponse.builder()
                .customerId(customerModel.getCustomerId())
                .firstName(customerModel.getFirstName())
                .lastName(customerModel.getLastName())
                .fullName(customerModel.getFullName())
                .age(customerModel.getAge())
                .mobileNumber(customerModel.getMobileNumber())
                .emailAddress(customerModel.getEmailAddress())
                .status(customerModel.getStatus())
                .addresses(modelToAddressResponse(models))
                .otp(otpModel.getOtpValue())
                .createdDate(customerModel.getCreatedDate())
                .updatedDate(customerModel.getUpdatedDate())
                .build();
    }

    /**
     * Converts a list of {@link AddressModel} entities into a list of {@link AddressResponse} objects.
     *
     * @param addresses list of address entities to be converted
     * @return a list of address response objects
     */
    private static List<AddressResponse> modelToAddressResponse(List<AddressModel> addresses) {
        List<AddressResponse> responseList = new ArrayList<>();
        addresses.forEach(address -> responseList.add(
                AddressResponse.builder()
                        .addressId(address.getAddressId())
                        .street(address.getStreet())
                        .city(address.getCity())
                        .state(address.getState())
                        .country(address.getCountry())
                        .addressType(address.getAddressType())
                        .pincode(address.getPincode())
                        .build()
                )
        );
    return responseList;
    }

    /**
     * Converts a single {@link CustomerModel} entity into a {@link CustomerResponse}
     * without OTP information. Useful for simple fetch operations.
     *
     * @param model the customer entity
     * @return a {@link CustomerResponse} object containing customer details
     */
    public static CustomerResponse toCustomerResponse(CustomerModel model) {
        return CustomerResponse.builder()
                .customerId(model.getCustomerId())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .fullName(model.getFullName())
                .age(model.getAge())
                .mobileNumber(model.getMobileNumber())
                .emailAddress(model.getEmailAddress())
                .status(model.getStatus())
                .createdDate(model.getCreatedDate())
                .updatedDate(model.getUpdatedDate())
                .addresses(modelToAddressResponse(model.getAddress()))
                .build();
    }
}
