package com.inventory_managerment.feature.product;
import com.inventory_managerment.feature.product.dto.ProductRequest;
import com.inventory_managerment.feature.product.dto.ProductResponse;
import com.inventory_managerment.feature.product.dto.ProductUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface ProductServices {
    ProductResponse getProductById(Long  id);
    Page<ProductResponse> getProducts(int page, int size);
    ProductResponse addProduct(ProductRequest productRequest);
    ProductResponse updateProduct(Long  id, ProductUpdateRequest productUpdateRequest);
    ResponseEntity<?> deleteProduct(Long id);
}
