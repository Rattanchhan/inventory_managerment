package com.inventory_managerment.feature.payment;
import com.inventory_managerment.feature.payment.dto.PaymentResponse;
import com.inventory_managerment.feature.payment.dto.PaymentUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    
    private final PaymentServices paymentServices;

    @PostMapping("/{id}/create")
    public PaymentResponse createPayment(@PathVariable("id") String id){
        return paymentServices.createPayment(Long.parseLong(id));
    }

    @GetMapping
    public Page<PaymentResponse> getAllPayments(@RequestParam("pageNumber") String pageNumber, @RequestParam("pageSize") String sizeNumber, @RequestParam("status") String status){
        return paymentServices.getAllPayments(Integer.parseInt(pageNumber),Integer.parseInt(sizeNumber),status);
    }

    @GetMapping("/{id}/find")
    public PaymentResponse getPayment(@PathVariable("id") String id){
        return paymentServices.getPaymentById(Long.parseLong(id));
    }

    @PatchMapping("/update")
    public PaymentResponse updatePayment(@RequestBody PaymentUpdateRequest paymentUpdateRequest){
        return paymentServices.updatePayment(paymentUpdateRequest);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deletePayment(@PathVariable("id") String id){
        return paymentServices.deletePayment(Long.parseLong(id));
    }
}
