package com.inventory_managerment.feature.payment;
import com.inventory_managerment.domain.Order;
import com.inventory_managerment.domain.Payment;
import com.inventory_managerment.feature.order.OrderRepository;
import com.inventory_managerment.feature.payment.dto.PaymentResponse;
import com.inventory_managerment.feature.payment.dto.PaymentUpdateRequest;
import com.inventory_managerment.mapper.payment.PaymentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentSerivcesImplementation implements PaymentServices{
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderRepository orderRepository;
    @Override
    public PaymentResponse updatePayment(PaymentUpdateRequest paymentUpdateRequest) {

        Payment payment = paymentRepository.findByOrderIdAndIsDeleted(paymentUpdateRequest.orderId(),false);

        //Validate payment update
        if(!payment.getStatus().equals("paid")){
            payment.setUpdatedAt(new Date());
            payment.setOrder(payment.getOrder());
            payment.setPaymentMethod(paymentUpdateRequest.paymentMethod());
            
            if(!payment.getPaidAmount().equals(payment.getPaymentAmount())){
                payment.setPaidAmount(payment.getPaidAmount().add(paymentUpdateRequest.amount()));
                payment.setOwnAmount(payment.getPaymentAmount().subtract(payment.getPaidAmount()));
            }
            else{
                payment.setOwnAmount(payment.getOwnAmount().subtract(paymentUpdateRequest.amount()));
                payment.setPaidAmount(payment.getPaidAmount().add(paymentUpdateRequest.amount()));
            }
        }
        else{
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED,"Payment has been already paid...");
        }
       
        //Validate status
        if(payment.getPaymentAmount().equals(payment.getPaidAmount())){
            payment.setStatus("paid");
        }
        else{
            payment.setStatus("pending");
        }

        payment = paymentRepository.save(payment);
        return paymentMapper.fromPayment(payment);
    }
    @Override
    public Page<PaymentResponse> getAllPayments(Integer pageNumber, Integer sizeNumber, String status) {
        PageRequest pageRequest = PageRequest.of(pageNumber,sizeNumber,Direction.ASC,"id");
        Page<Payment> paymentPage = paymentRepository.findAllBystatusContainingAndIsDeleted(pageRequest,status,false);
        return paymentPage.map(paymentMapper::fromPayment);
    }
    @Override
    public PaymentResponse getPaymentById(Long id) {
        return paymentMapper.fromPayment(paymentRepository.findByPaymentIdAndIsDeleted(id,false).orElseThrow(
            ()->new  ResponseStatusException(HttpStatus.NOT_FOUND,"Payment has not been found...")
            ));
    }
    @Override
    public ResponseEntity<?> deletePayment(Long id) {
        Payment payment = paymentRepository.findByPaymentIdAndIsDeleted(id,false).orElseThrow(()->
        new ResponseStatusException(HttpStatus.NOT_FOUND,"Payment has not been found"));

        payment.setIsDeleted(true);
        paymentRepository.save(payment);
        return ResponseEntity.status(HttpStatus.OK)
        .body(Map.of("Deleted",Boolean.TRUE));
    }
    @Override
    public PaymentResponse createPayment(Long id) {
        Payment payment;
        Order order ;
        //Validate order
        order = orderRepository.findByIdAndIsDelete(id,false);
        if(order==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Order has been not found...");
        }
        //Validate payment
        else{
            payment = paymentRepository.findByOrderIdAndIsDeleted(order.getId(),false);
            if(payment==null){
                IComment get = paymentRepository.getTotalAmount(id);
                payment  = new Payment();
                payment.setOrder(order);
                payment.setTotalAmount(get.getTotalAmount());
                payment.setTotalDiscount(get.getTotalDiscount());
                payment.setPaymentAmount(get.getTotalAmount().subtract(get.getTotalDiscount()));
                payment.setPaymentMethod("cash");
                payment.setStatus("failed");
                payment.setPaidAmount(BigDecimal.valueOf(0.00));
                payment.setOwnAmount(BigDecimal.valueOf(0.00));
                payment.setCreatedAt(new Date());
                payment.setUpdatedAt(new Date());
                payment.setIsDeleted(false);

                payment=paymentRepository.save(payment);
            }
            else{
                throw new ResponseStatusException(HttpStatus.FOUND,"Payment has been already existing...");
            }
        }
        return paymentMapper.fromPayment(payment);
    }
    
}
