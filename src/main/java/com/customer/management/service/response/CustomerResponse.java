package com.customer.management.service.response;

import com.customer.management.service.entity.CustomerAddress;
import com.customer.management.service.enums.CustomerStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
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
