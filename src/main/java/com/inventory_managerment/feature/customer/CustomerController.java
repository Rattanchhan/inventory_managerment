package com.inventory_managerment.feature.customer;
import com.inventory_managerment.feature.customer.dto.CustomerRequest;
import com.inventory_managerment.feature.customer.dto.CustomerResponse;
import com.inventory_managerment.feature.customer.dto.CustomerUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerResponse createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        return customerService.addCustomer(customerRequest);
    }
    @GetMapping("/list")
    public List<CustomerResponse> getAllCustomer(){
        return customerService.getAllCustomers();
    }
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{phone}/find")
    public CustomerResponse getCustomer(@PathVariable String phone){
        return customerService.getCustomerByPhone(phone);
    }
    @PatchMapping("/{id}/update")
    public CustomerResponse updateCustomer(@PathVariable String id, @Valid @RequestBody CustomerUpdateRequest customerUpdateRequest){
        return customerService.updateCustomer(Integer.parseInt(id),customerUpdateRequest);
    }
    @GetMapping("page")
    public Page<CustomerResponse> getCustomer(@RequestParam(required = false,value = "pageNumber",defaultValue="0") String page, @RequestParam(required = false,value ="pageSize",defaultValue = "25") String size){
        return customerService.getCustomers(Integer.parseInt(page),Integer.parseInt(size));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id){
        return customerService.deleteCustomer(Integer.parseInt(id));
    }
}
