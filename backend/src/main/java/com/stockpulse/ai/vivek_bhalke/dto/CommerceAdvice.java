package com.stockpulse.ai.vivek_bhalke.dto;

import java.math.BigDecimal;

public class CommerceAdvice {
    private BigDecimal suggestedPrice;
    private Integer suggestedReorderQuantity;
    private Double confidenceScore;
    private String reasoning;
    
    // Constructors
    public CommerceAdvice() {}
    
    public CommerceAdvice(BigDecimal suggestedPrice, Integer suggestedReorderQuantity, 
                         Double confidenceScore, String reasoning) {
        this.suggestedPrice = suggestedPrice;
        this.suggestedReorderQuantity = suggestedReorderQuantity;
        this.confidenceScore = confidenceScore;
        this.reasoning = reasoning;
    }
    
    // Getters and Setters
    public BigDecimal getSuggestedPrice() {
        return suggestedPrice;
    }
    
    public void setSuggestedPrice(BigDecimal suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
    }
    
    public Integer getSuggestedReorderQuantity() {
        return suggestedReorderQuantity;
    }
    
    public void setSuggestedReorderQuantity(Integer suggestedReorderQuantity) {
        this.suggestedReorderQuantity = suggestedReorderQuantity;
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
}