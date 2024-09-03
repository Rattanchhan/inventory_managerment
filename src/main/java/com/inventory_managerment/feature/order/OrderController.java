package com.inventory_managerment.feature.order;
import com.inventory_managerment.feature.order.dto.OrderRequest;
import com.inventory_managerment.feature.order.dto.OrderRequestUpdate;
import com.inventory_managerment.feature.order.dto.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderServices orderServices;
    @GetMapping
    public Page<OrderResponse> getOrders(@RequestParam(required = false,value = "pageNumber",defaultValue="0") String page, @RequestParam(required = false,value ="pageSize",defaultValue = "25") String size){
        return orderServices.getOrders(Integer.parseInt(page),Integer.parseInt(size));
    }
    @GetMapping("/{id}/find")
    public OrderResponse getOrder(@PathVariable(name = "id") String id){
        return orderServices.getOrder(Long.parseLong(id));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse addOrder(@Valid @RequestBody OrderRequest orderRequest){
        return
         orderServices.addOrder(orderRequest);
    }
    @PatchMapping("/{id}/update")
    public OrderResponse updateOrder(@PathVariable(name="id") String id,@Valid @RequestBody OrderRequestUpdate orderRequestUpdate){
        return orderServices.updateOrder(Long.parseLong(id), orderRequestUpdate);
    }
    
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteOrder(@PathVariable(name="id") String id){
        return orderServices.deleteOrder(Long.parseLong(id));
    }
}
