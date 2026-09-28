package main.java.com.stockpulse.ai.vivek_bhalke.strategy;

import main.java.com.stockpulse.ai.vivek_bhalke.strategy.impl.RuleBasedCommerceAdvisor;
import main.java.com.stockpulse.ai.vivek_bhalke.strategy.impl.AICommerceAdvisor;
import org.springframework.stereotype.Component;

@Component
public class StrategyFactory {
    
    private final RuleBasedCommerceAdvisor ruleBased;
    private final AICommerceAdvisor aiAdvisor;
    
    // Simulating a dynamic configuration toggle (could be updated via an API endpoint at runtime)
    private String activeStrategyConfig = "RULE_BASED"; 
    
    public StrategyFactory(RuleBasedCommerceAdvisor ruleBased, AICommerceAdvisor aiAdvisor) {
        this.ruleBased = ruleBased;
        this.aiAdvisor = aiAdvisor;
    }
    
    // Allows your frontend/controller to switch it instantly on-the-fly
    public void setActiveStrategyConfig(String strategy) {
        this.activeStrategyConfig = strategy;
    }
    
    public CommerceAdvisor getActiveStrategy() {
        if ("AI".equalsIgnoreCase(activeStrategyConfig)) {
            return aiAdvisor;
        }
        return ruleBased;
    }
}