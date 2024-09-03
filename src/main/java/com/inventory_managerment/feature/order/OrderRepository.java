package com.inventory_managerment.feature.order;
import com.inventory_managerment.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order,Long>{

    @Query(value = "SELECT o FROM Order AS o WHERE o.id=:id AND o.isDelete = false")
    Order findId(Long id);

    Order findByIdAndIsDelete(Long id ,Boolean isDelete);

    Page<Order> findAllByIsDelete(PageRequest pageRequest,Boolean isDelete);
    
}
