package main.java.com.stockpulse.ai.vivek_bhalke.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_snapshots")
public class InventorySnapshot {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "product_id", nullable = false)
    private String productId;
    
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    
    @Column(name = "stock_level")
    private Integer stockLevel;
    
    @Column(name = "reserved_stock")
    private Integer reservedStock;
    
    @Column(name = "available_stock")
    private Integer availableStock;
    
    @Column(name = "last_order_timestamp")
    private LocalDateTime lastOrderTimestamp;
    
    // Constructors
    public InventorySnapshot() {}
    
    public InventorySnapshot(String productId, Integer stockLevel) {
        this.productId = productId;
        this.stockLevel = stockLevel;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public Integer getStockLevel() {
        return stockLevel;
    }
    
    public void setStockLevel(Integer stockLevel) {
        this.stockLevel = stockLevel;
    }
    
    public Integer getReservedStock() {
        return reservedStock;
    }
    
    public void setReservedStock(Integer reservedStock) {
        this.reservedStock = reservedStock;
    }
    
    public Integer getAvailableStock() {
        return availableStock;
    }
    
    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
    }
    
    public LocalDateTime getLastOrderTimestamp() {
        return lastOrderTimestamp;
    }
    
    public void setLastOrderTimestamp(LocalDateTime lastOrderTimestamp) {
        this.lastOrderTimestamp = lastOrderTimestamp;
    }
}