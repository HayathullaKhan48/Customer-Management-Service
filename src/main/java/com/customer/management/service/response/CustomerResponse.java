package com.customer.management.service.response;

import com.customer.management.service.entity.CustomerAddress;
import com.customer.management.service.enums.CustomerStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CustomerResponse is a Data Transfer Object (DTO) that is used to send
 * customer-related data back to the client as a response.
 * This class is designed to keep the API response clean and separate from the
 * entity classes (database models), making it easier to control what data
 * is exposed externally.
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {
    private Long customerId;
    private String firstName;
    private String lastName;
    private String fullName;
    private Integer age;
    private String mobileNumber;
    private String emailAddress;
    private CustomerStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<CustomerAddress> addresses;
}
