package com.customer.management.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * CustomerAddress entity represents a customer's address information.
 *  Key Points:
 * - Mapped to table: customer_address
 * - Each address belongs to a single customer (Many-to-One relation)
 * - Uses Lombok for boilerplate (getters, setters, constructors, builder)
 * - Many-to-One mapping with CustomerModel.
 * - FetchType. LAZY → loads customer only when explicitly accessed.
 *  - @JsonIgnore → avoids infinite recursion during JSON serialization.
 */
@Entity
@Table(name = "customer_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "addressType", nullable = false)
    private String addressType;

    @Column(name = "pincode", nullable = false)
    private Long pincode;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerModel customer;
}
