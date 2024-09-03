package com.inventory_managerment.feature.product;
import com.inventory_managerment.domain.Product;
import com.inventory_managerment.feature.product.dto.ProductRequest;
import com.inventory_managerment.feature.product.dto.ProductResponse;
import com.inventory_managerment.feature.product.dto.ProductUpdateRequest;
import com.inventory_managerment.mapper.product.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImplementation implements ProductServices{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @Override
    public ProductResponse getProductById(Long id) {
        Product product= productRepository
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product has not been found"));
        return productMapper.toProductResponse(product);
    }

    @Override
    public Page<ProductResponse> getProducts(int page, int size){
        Sort sortName = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sortName);
        Page<Product> products = productRepository.findAll(pageRequest);
        return products.map(productMapper::toProductResponse);
    }

    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {
        Product  product = productMapper.fromProductRequest(productRequest);
        //Set by system
        product.setSku(UUID.randomUUID().toString());
        product.setCreatedBy(1);
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        product = productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductUpdateRequest productUpdateRequest) {
        Product product= productRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product has not been found"));
        productMapper.fromProductUpdateRequest(productUpdateRequest,product);
        product.setUpdatedAt(new Date());
        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    @Override
    public ResponseEntity<?> deleteProduct(Long id) {
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("Deleted",Boolean.TRUE));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product has not been found");
    }
}
