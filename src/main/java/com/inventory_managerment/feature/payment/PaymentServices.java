package com.inventory_managerment.feature.payment;
import com.inventory_managerment.feature.payment.dto.PaymentResponse;
import com.inventory_managerment.feature.payment.dto.PaymentUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface PaymentServices {
    
    PaymentResponse updatePayment(PaymentUpdateRequest paymentUpdateRequest);
    Page<PaymentResponse> getAllPayments(Integer pageNumber, Integer sizeNumber, String status);
    PaymentResponse getPaymentById(Long id);
    ResponseEntity<?> deletePayment(Long id);

    PaymentResponse createPayment(Long id);
}
