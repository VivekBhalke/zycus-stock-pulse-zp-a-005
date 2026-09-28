package main.java.com.stockpulse.ai.vivek_bhalke.strategy.impl;

import main.java.com.stockpulse.ai.vivek_bhalke.entity.Product;
import main.java.com.stockpulse.ai.vivek_bhalke.dto.ReorderAdvice;
import main.java.com.stockpulse.ai.vivek_bhalke.strategy.ReorderStrategy;
import org.springframework.stereotype.Component;

@Component
public class AIReorderStrategy implements ReorderStrategy {
    
    @Override
    public ReorderAdvice calculateReorderAdvice(Product product) {
        // TODO: Implement AI-based reorder logic
        // For now, falling back to rule-based logic
        return new ReorderAdvice(
            0,
            0.0,
            "AI strategy not yet implemented. Using zero quantity as placeholder."
        );
    }
}