package com.inventory_managerment.feature.orderItem;
import com.inventory_managerment.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
    
}
