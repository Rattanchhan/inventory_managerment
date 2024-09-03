package com.inventory_managerment.feature.order;
import com.inventory_managerment.feature.order.dto.OrderRequest;
import com.inventory_managerment.feature.order.dto.OrderRequestUpdate;
import com.inventory_managerment.feature.order.dto.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface OrderServices {
    Page<OrderResponse> getOrders(Integer page, Integer size);
    OrderResponse getOrder(Long id);
    OrderResponse addOrder(OrderRequest orderRequest);
    OrderResponse updateOrder(Long id, OrderRequestUpdate orderRequestUpdate);
    ResponseEntity<?> deleteOrder(Long id);
}
