package com.stockpulse.ai.vivek_bhalke.dto;

import java.math.BigDecimal;

public class PricingAdvice {
    private BigDecimal suggestedPrice;
    private Double confidenceScore;
    private String reasoning;
    
    // Constructors
    public PricingAdvice() {}
    
    public PricingAdvice(BigDecimal suggestedPrice, Double confidenceScore, String reasoning) {
        this.suggestedPrice = suggestedPrice;
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