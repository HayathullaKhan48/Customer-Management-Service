package com.customer.management.service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CustomerAddressResponse is a DTO used to send address details
 * in a safe and clean format to the client.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponse {

    private Long addressId;
    private String street;
    private String city;
    private String state;
    private String country;
    private String addressType;
    private Long pincode;

}
