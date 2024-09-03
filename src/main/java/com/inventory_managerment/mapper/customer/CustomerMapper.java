package com.inventory_managerment.mapper.customer;
import com.inventory_managerment.domain.Customer;
import com.inventory_managerment.feature.customer.dto.CustomerRequest;
import com.inventory_managerment.feature.customer.dto.CustomerResponse;
import com.inventory_managerment.feature.customer.dto.CustomerUpdateRequest;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    List<CustomerResponse> toCustomerResponse(List<Customer> customer);
    CustomerResponse toCustomerResponse(Customer customer);
    Customer fromCustomerRequest(CustomerRequest customerRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromCustomerUpdateRequest(CustomerUpdateRequest customerUpdateRequest, @MappingTarget Customer customer);
}
