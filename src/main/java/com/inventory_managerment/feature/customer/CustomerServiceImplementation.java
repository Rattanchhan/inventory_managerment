package com.inventory_managerment.feature.customer;
import com.inventory_managerment.domain.Customer;
import com.inventory_managerment.feature.customer.dto.CustomerRequest;
import com.inventory_managerment.feature.customer.dto.CustomerResponse;
import com.inventory_managerment.feature.customer.dto.CustomerUpdateRequest;
import com.inventory_managerment.mapper.customer.CustomerMapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CustomerServiceImplementation implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private List<Customer> customers;
    CustomerServiceImplementation(CustomerRepository customerRepository,CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        reloadFromDataBase();
    }
    public void reloadFromDataBase(){
        customers = customerRepository.findAll();
    }
    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerMapper.toCustomerResponse(customers);
    }

    @Override
    public CustomerResponse getCustomerByPhone(String phoneNumber) {
        Customer customer = customerRepository
                .findByPhone(phoneNumber)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer has not been found"));
        return customerMapper.toCustomerResponse(customer);
    }

    @Override
    public Page<CustomerResponse> getCustomers(int page, int size) {
        Sort sortName = Sort.by(Sort.Direction.ASC, "name");
        PageRequest pageRequest = PageRequest.of(page, size, sortName);
        //get offset
        int start= (int)pageRequest.getOffset();
        log.info("Start: {}",start);
        int end = Math.min((start+pageRequest.getPageSize()),this.customers.size());
        log.info("Page size: {},End: {}",pageRequest.getPageSize(),end);
//        Page<Customer> customers = customerRepository.findAll(pageRequest);
        Page<Customer> customers = new PageImpl<Customer>(this.customers.subList(start, end), pageRequest,this.customers.size());
        return customers.map(customerMapper::toCustomerResponse);
    }

    @Override
    public CustomerResponse addCustomer(CustomerRequest customerRequest){
        Customer customer = customerMapper.fromCustomerRequest(customerRequest);
        customer.setCreatedAt(new Date());
        customer.setUpdatedAt(new Date());
        customer=customerRepository.save(customer);
//        reloadFromDataBase();
        return customerMapper.toCustomerResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(Integer id, CustomerUpdateRequest customerUpdateRequest) {
        Customer customer= customerRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer has not been found"));
        log.info("Before update: {},{},{},{},{}",customer.getName(),customer.getPhone(),
        customer.getPoint(),customer.getCreatedAt(),customer.getCreatedAt());
        customer.setUpdatedAt(new Date());
        customerMapper.fromCustomerUpdateRequest(customerUpdateRequest,customer);
        log.info("After update: {},{},{},{},{}",customer.getName(),customer.getPhone(),
                customer.getPoint(),customer.getUpdatedAt(),customer.getUpdatedAt());
        customerRepository.save(customer);
    return customerMapper.toCustomerResponse(customer);
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteCustomer(Integer id) {
        if(customerRepository.existsById(id)){
            customerRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("Deleted",Boolean.TRUE));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer has not been found");
    }
}
