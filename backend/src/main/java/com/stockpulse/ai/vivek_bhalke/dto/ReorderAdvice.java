package main.java.com.stockpulse.ai.vivek_bhalke.dto;

public class ReorderAdvice {
    private Integer suggestedQuantity;
    private Double confidenceScore;
    private String reasoning;
    
    // Constructors
    public ReorderAdvice() {}
    
    public ReorderAdvice(Integer suggestedQuantity, Double confidenceScore, String reasoning) {
        this.suggestedQuantity = suggestedQuantity;
        this.confidenceScore = confidenceScore;
        this.reasoning = reasoning;
    }
    
    // Getters and Setters
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
}