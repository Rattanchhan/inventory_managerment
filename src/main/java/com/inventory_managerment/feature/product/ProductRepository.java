package com.inventory_managerment.feature.product;
import com.inventory_managerment.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<Product, Long> {
}
