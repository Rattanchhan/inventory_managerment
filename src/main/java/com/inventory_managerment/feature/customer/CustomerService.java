package com.inventory_managerment.feature.customer;
import com.inventory_managerment.feature.customer.dto.CustomerRequest;
import com.inventory_managerment.feature.customer.dto.CustomerResponse;
import com.inventory_managerment.feature.customer.dto.CustomerUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> getAllCustomers();
    CustomerResponse getCustomerByPhone(String phoneNumber);
    Page<CustomerResponse> getCustomers(int page, int size);
    CustomerResponse addCustomer(CustomerRequest customerRequest);
    CustomerResponse updateCustomer(Integer id, CustomerUpdateRequest customerUpdateRequest);
    ResponseEntity<?>deleteCustomer(Integer id);
}
