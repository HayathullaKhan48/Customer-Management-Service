package com.customer.management.service.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Request class for customer address.
 * Includes customerId so it can be linked to a specific customer.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressRequest {

    @NotBlank(message = "Street cannot be blank")
    private String street;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotBlank(message = "State cannot be blank")
    private String state;

    @NotBlank(message = "AddressType cannot be blank")
    private String addressType;

    @NotNull(message = "pinCode cannot be blank")
    @Min(value = 10000, message = "Pincode must be at least 10000")
    @Max(value = 9999999, message = "Pincode must be at most 9999999")
    private Long pincode;

    @NotBlank(message = "Country cannot be blank")
    private String country;
}
