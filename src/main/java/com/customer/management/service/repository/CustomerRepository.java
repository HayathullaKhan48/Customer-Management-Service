package com.customer.management.service.repository;

import com.customer.management.service.entity.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repository for CustomerModel entity.
 * Contains custom find and existence checks.
 * Find customer by ID
 * Find customer by mobile number
 * Check if mobile number already exists
 * Check if email address already exists
 * Check if full name already exists
 */
public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {
    Optional<CustomerModel> findByCustomerId(Long customerId);
    Optional<CustomerModel> findCustomerByMobileNumber(String mobileNumber);
    Optional<CustomerModel> findCustomerByEmailAddress(String emailAddress);
    Optional<CustomerModel> findCustomerByFullName(String fullName);
    boolean existsByMobileNumber(String mobileNumber);
    boolean existsByEmailAddress(String emailAddress);
    boolean existsByFullName(String fullName);

    @Modifying
    @Query("UPDATE CustomerModel c SET c.mobileNumber = :mobileNumber WHERE c.customerId = :customerId")
    int updateMobileNumberByCustomerId(@Param("customerId") Long customerId, @Param("mobileNumber") String mobileNumber);

    @Modifying
    @Query("UPDATE CustomerModel c SET c.emailAddress = :emailAddress WHERE c.customerId = :customerId")
    int updateEmailAddressByCustomerId(@Param("customerId") Long customerId, @Param("emailAddress") String emailAddress);

    @Modifying
    @Query("UPDATE CustomerModel c SET c.password = :password WHERE c.customerId = :customerId")
    int updatePasswordByCustomerId(@Param("customerId") Long customerId, @Param("password") String password);

    @Modifying
    @Query("DELETE FROM CustomerModel c WHERE c.customerId = :customerId")
    int deleteCustomerByCustomerId(@Param("customerId") Long customerId);

}
