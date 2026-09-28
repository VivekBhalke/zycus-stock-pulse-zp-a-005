package main.java.com.stockpulse.ai.vivek_bhalke.strategy.impl;

import main.java.com.stockpulse.ai.vivek_bhalke.entity.Product;
import main.java.com.stockpulse.ai.vivek_bhalke.dto.PricingAdvice;
import main.java.com.stockpulse.ai.vivek_bhalke.strategy.PricingStrategy;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class RuleBasedPricingStrategy implements PricingStrategy {
    
    @Override
    public PricingAdvice calculatePricingAdvice(Product product) {
        BigDecimal suggestedPrice;
        Double confidenceScore;
        String reasoning;
        
        // Rule-based pricing logic
        if (product.getStockLevel() < product.getReorderThreshold()) {
            // If stock is low, recommend 10% price increase
            suggestedPrice = product.getCurrentPrice().multiply(new BigDecimal("1.10"));
            confidenceScore = 0.9;
            reasoning = "Low stock level (" + product.getStockLevel() + ") detected. Recommending 10% price increase to optimize revenue.";
        } else if (product.getDemandVelocity() > getCategoryAverage(product.getCategory()) * 2) {
            // If demand velocity is high, recommend 5% price increase
            suggestedPrice = product.getCurrentPrice().multiply(new BigDecimal("1.05"));
            confidenceScore = 0.85;
            reasoning = "High demand velocity (" + product.getDemandVelocity() + ") detected, 2x category average. Recommending 5% price increase.";
        } else {
            // Otherwise, hold current price
            suggestedPrice = product.getCurrentPrice();
            confidenceScore = 0.7;
            reasoning = "Stable inventory and demand conditions. No price change recommended.";
        }
        
        return new PricingAdvice(suggestedPrice, confidenceScore, reasoning);
    }
    
    private double getCategoryAverage(String category) {
        // Placeholder implementation - would typically fetch from a config or database
        switch (category.toUpperCase()) {
            case "ELECTRONICS":
                return 5.0;
            case "APPAREL":
                return 8.0;
            case "HOME":
                return 3.0;
            default:
                return 5.0;
        }
    }
}