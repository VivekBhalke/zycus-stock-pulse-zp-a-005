package main.java.com.stockpulse.ai.vivek_bhalke.strategy.impl;

import main.java.com.stockpulse.ai.vivek_bhalke.entity.Product;
import main.java.com.stockpulse.ai.vivek_bhalke.dto.ReorderAdvice;
import main.java.com.stockpulse.ai.vivek_bhalke.strategy.ReorderStrategy;
import org.springframework.stereotype.Component;

@Component
public class RuleBasedReorderStrategy implements ReorderStrategy {
    
    @Override
    public ReorderAdvice calculateReorderAdvice(Product product) {
        Integer suggestedQuantity;
        Double confidenceScore;
        String reasoning;
        
        // Rule-based reorder logic
        // Recommend quantity = (reorder threshold × 3) − current stock, minimum 1
        int calculatedQuantity = (product.getReorderThreshold() * 3) - product.getStockLevel();
        suggestedQuantity = Math.max(1, calculatedQuantity);
        
        confidenceScore = 0.8;
        reasoning = "Calculated reorder quantity using formula: (reorder threshold × 3) − current stock = (" + 
                   product.getReorderThreshold() + " × 3) − " + product.getStockLevel() + " = " + calculatedQuantity + 
                   ". Minimum quantity enforced.";
        
        return new ReorderAdvice(suggestedQuantity, confidenceScore, reasoning);
    }
}