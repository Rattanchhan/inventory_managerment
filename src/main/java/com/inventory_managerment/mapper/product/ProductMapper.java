package com.inventory_managerment.mapper.product;
import com.inventory_managerment.domain.Product;
import com.inventory_managerment.feature.product.dto.ProductRequest;
import com.inventory_managerment.feature.product.dto.ProductResponse;
import com.inventory_managerment.feature.product.dto.ProductUpdateRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toProductResponse(Product product);
    Product fromProductRequest(ProductRequest productRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromProductUpdateRequest(ProductUpdateRequest productUpdateRequest, @MappingTarget Product product);
}
