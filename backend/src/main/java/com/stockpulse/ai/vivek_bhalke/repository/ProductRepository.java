package com.stockpulse.ai.vivek_bhalke.repository;

import com.stockpulse.ai.vivek_bhalke.entity.Product;
import com.stockpulse.ai.vivek_bhalke.entity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByStatus(ProductStatus status);
    
    List<Product> findByCategory(String category);
    
    List<Product> findByStatusAndCategory(ProductStatus status, String category);
    
    @Query("SELECT p FROM Product p WHERE " +
           "(:status IS NULL OR p.status = :status) AND " +
           "(:category IS NULL OR p.category = :category)")
    List<Product> findByStatusAndCategoryWithFilters(
        @Param("status") ProductStatus status,
        @Param("category") String category
    );
}
