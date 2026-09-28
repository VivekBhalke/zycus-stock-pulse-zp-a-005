package com.stockpulse.ai.vivek_bhalke.entity;

import com.stockpulse.ai.vivek_bhalke.entity.SuggestionStatus;
import com.stockpulse.ai.vivek_bhalke.entity.TriggerReason;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reorder_suggestions")
public class ReorderSuggestion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "product_id", nullable = false)
    private String productId;
    
    @Column(name = "suggested_quantity")
    private Integer suggestedQuantity;
    
    @Column(name = "confidence_score")
    private Double confidenceScore;
    
    @Column(name = "reasoning", length = 1000)
    private String reasoning;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "trigger_reason")
    private TriggerReason triggerReason;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SuggestionStatus status;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructors
    public ReorderSuggestion() {}
    
    public ReorderSuggestion(String productId, Integer suggestedQuantity, TriggerReason triggerReason) {
        this.productId = productId;
        this.suggestedQuantity = suggestedQuantity;
        this.triggerReason = triggerReason;
        this.status = SuggestionStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
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
    
    public Integer getSuggestedQuantity() {
        return suggestedQuantity;
    }
    
    public void setSuggestedQuantity(Integer suggestedQuantity) {
        this.suggestedQuantity = suggestedQuantity;
    }
    
    public Double getConfidenceScore() {
        return confidenceScore;
    }
    
    public void setConfidenceScore(Double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }
    
    public String getReasoning() {
        return reasoning;
    }
    
    public void setReasoning(String reasoning) {
        this.reasoning = reasoning;
    }
    
    public TriggerReason getTriggerReason() {
        return triggerReason;
    }
    
    public void setTriggerReason(TriggerReason triggerReason) {
        this.triggerReason = triggerReason;
    }
    
    public SuggestionStatus getStatus() {
        return status;
    }
    
    public void setStatus(SuggestionStatus status) {
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