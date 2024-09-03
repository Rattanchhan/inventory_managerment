package com.inventory_managerment.feature.customer;
import com.inventory_managerment.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByPhone(String phone);
    Boolean existsByPhone(String phone);
}
