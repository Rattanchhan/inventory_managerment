package com.inventory_managerment.mapper.order;
import com.inventory_managerment.domain.Order;
import com.inventory_managerment.domain.OrderItem;
import com.inventory_managerment.feature.order.dto.OrderRequest;
import com.inventory_managerment.feature.order.dto.OrderRequestUpdate;
import com.inventory_managerment.feature.order.dto.OrderResponse;
import com.inventory_managerment.feature.orderItem.dto.OrderItemsResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel="spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "user.id",target = "staffId")
    @Mapping(source ="user.name",target = "staffName")
    @Mapping(source="user.position",target ="position")
    @Mapping(source = "customer.id",target = "customerId")
    @Mapping(source="customer.name",target = "customerName")
    @Mapping(source = "customer.phone",target = "phone")
    @Mapping(source="customer.point",target = "point")
    OrderResponse toOrderResponse(Order order);

    @Mapping(source = "product.id", target = "productId")
    OrderItemsResponse toOrderItemsResponse(OrderItem orderItem);

    Order fromOrderRequest(OrderRequest orderRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromOrderUpdateRequest(OrderRequestUpdate orderRequestUpdate, @MappingTarget Order order);

    
}
