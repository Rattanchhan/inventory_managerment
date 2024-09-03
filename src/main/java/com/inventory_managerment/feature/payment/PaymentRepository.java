package com.inventory_managerment.feature.payment;
import com.inventory_managerment.domain.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    @Query(value = "SELECT p FROM Payment as p JOIN FETCH p.order WHERE p.order.id = :id AND p.isDeleted= :isDeleted")
    Payment findByOrderIdAndIsDeleted(@Param("id") Long id, @Param("isDeleted") Boolean isDeleted);
    
    Page<Payment> findAllBystatusContainingAndIsDeleted(PageRequest pageRequest,String status,Boolean isDeleted);

    @Query(value = "SELECT p FROM Payment as p JOIN FETCH p.order WHERE p.id = :id AND p.isDeleted= :isDeleted")
    Optional<Payment> findByPaymentIdAndIsDeleted(@Param("id") Long id,@Param("isDeleted") Boolean isDeleted);

    @Query(value = "SELECT SUM(oi.qty*oi.unitPrice) AS totalAmount,SUM(oi.discountAmount) AS totalDiscount FROM OrderItem as oi WHERE oi.order.id = :id")
    IComment getTotalAmount(@Param("id") Long id);
}
