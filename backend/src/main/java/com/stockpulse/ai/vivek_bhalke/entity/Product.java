package com.stockpulse.ai.vivek_bhalke.entity;

import com.stockpulse.ai.vivek_bhalke.entity.ProductStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {
    
    @Id
    private String id;
    
    @Column(name = "sku", unique = true, nullable = false)
    private String sku;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "category", nullable = false)
    private String category;
    
    @Column(name = "current_price", precision = 10, scale = 2)
    private BigDecimal currentPrice;
    
    @Column(name = "stock_level")
    private Integer stockLevel;
    
    @Column(name = "reorder_threshold")
    private Integer reorderThreshold;
    
    @Column(name = "demand_velocity")
    private Integer demandVelocity;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProductStatus status;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructors
    public Product() {}
    
    public Product(String id, String sku, String name, String category) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.category = category;
        this.status = ProductStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getSku() {
        return sku;
    }
    
    public void setSku(String sku) {
        this.sku = sku;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }
    
    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }
    
    public Integer getStockLevel() {
        return stockLevel;
    }
    
    public void setStockLevel(Integer stockLevel) {
        this.stockLevel = stockLevel;
    }
    
    public Integer getReorderThreshold() {
        return reorderThreshold;
    }
    
    public void setReorderThreshold(Integer reorderThreshold) {
        this.reorderThreshold = reorderThreshold;
    }
    
    public Integer getDemandVelocity() {
        return demandVelocity;
    }
    
    public void setDemandVelocity(Integer demandVelocity) {
        this.demandVelocity = demandVelocity;
    }
    
    public ProductStatus getStatus() {
        return status;
    }
    
    public void setStatus(ProductStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
}
