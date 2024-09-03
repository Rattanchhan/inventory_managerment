package com.inventory_managerment.feature.order;
import java.util.Date;
import com.inventory_managerment.domain.*;
import com.inventory_managerment.feature.customer.CustomerRepository;
import com.inventory_managerment.feature.order.dto.OrderRequest;
import com.inventory_managerment.feature.order.dto.OrderRequestUpdate;
import com.inventory_managerment.feature.order.dto.OrderResponse;
import com.inventory_managerment.feature.orderItem.OrderItemRepository;
import com.inventory_managerment.feature.product.ProductRepository;
import com.inventory_managerment.feature.product.dto.ProductUserRequest;
import com.inventory_managerment.feature.user.UserRepository;
import com.inventory_managerment.mapper.order.OrderMapper;
import com.inventory_managerment.mapper.orderItems.OrderItemsMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImplementation implements OrderServices{
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemsMapper orderItemsMapper;
    private final ProductRepository productRepository;
    private Product product;
    private OrderItem orderItem;

    @Override
    public Page<OrderResponse> getOrders(Integer page, Integer size){
        Sort sortId = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sortId);
        Page<Order> orders= orderRepository.findAllByIsDelete(pageRequest,false);
        return orders.map(orderMapper::toOrderResponse);
    }
    
    @Override
    public OrderResponse getOrder(Long id){

        Order order = orderRepository.findByIdAndIsDelete(id,false);
        if(order == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Order has not been found");
        }
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public OrderResponse addOrder(OrderRequest orderRequest){
        //Validate customer
        Customer customer = customerRepository.findById(orderRequest.customerId()).
        orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer has not been found"));

        //Validate user
        User user =userRepository.findById(orderRequest.userId()).
            orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"User has not been found"));
        
        //Transer DTO to Domain Model
        Order order = orderMapper.fromOrderRequest(orderRequest);
        
        order.setCustomer(customer);
        order.setUser(user);
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());
        order.setIsDelete(false);
        order = orderRepository.save(order);

        for(ProductUserRequest productUserRequest:orderRequest.products()){

            orderItem  = orderItemsMapper.toOrderItem(productUserRequest);
            product = productRepository.findById(productUserRequest.productId())
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Order has not been found"));

            if(product.getStockQty()<productUserRequest.qty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("product id %d out of stock",product.getId()));
            }
            
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setCreatedAt(new Date());
            orderItem.setUpdatedAt(new Date());

            product.setStockQty(product.getStockQty()-productUserRequest.qty());

            productRepository.save(product);
            orderItemRepository.save(orderItem);
            order.getOrderItems().add(orderItem);
            
        }

        order = orderRepository.save(order);
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderRequestUpdate orderRequestUpdate){
        Order order = orderRepository.findById(id)
                      .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Order has not been found"));
        orderMapper.fromOrderUpdateRequest(orderRequestUpdate, order);
        order.setUpdatedAt(new Date());
        orderRepository.save(order);
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public ResponseEntity<?> deleteOrder(Long id){
        Order order = orderRepository.findById(id)
        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Order has not been found"));

        order.setIsDelete(true);
        orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("Deleted",Boolean.TRUE));
    }
}
