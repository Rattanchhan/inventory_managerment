package com.inventory_managerment.feature.product;
import com.inventory_managerment.feature.product.dto.ProductRequest;
import com.inventory_managerment.feature.product.dto.ProductResponse;
import com.inventory_managerment.feature.product.dto.ProductUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductServices productServices;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest productRequest) throws Exception {
        return productServices.addProduct(productRequest);
    }
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{id}/find")
    public ProductResponse getUser(@PathVariable String id){
        return productServices.getProductById(Long.parseLong(id));
    }
    @PatchMapping("/{id}/update")
    public ProductResponse updateProduct(@PathVariable String id, @Valid @RequestBody ProductUpdateRequest productUpdateRequest){
        return productServices.updateProduct(Long.parseLong(id),productUpdateRequest);
    }
    @GetMapping
    public Page<ProductResponse> getProducts(@RequestParam(required = false,value = "pageNumber",defaultValue="0") String page, @RequestParam(required = false,value ="pageSize",defaultValue = "25") String size){
        return productServices.getProducts(Integer.parseInt(page),Integer.parseInt(size));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        return productServices.deleteProduct(Long.parseLong(id));
    }
}