package com.inventory_managerment.mapper.orderItems;
import java.util.List;

import com.inventory_managerment.domain.OrderItem;
import com.inventory_managerment.feature.product.dto.ProductUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface OrderItemsMapper {

    List<OrderItem> fromProductUserRequest(List<ProductUserRequest> productUserRequest);

    @Mapping(target = "id",ignore = true)
    OrderItem toOrderItem(ProductUserRequest productUserRequest);
}
