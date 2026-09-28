package main.java.com.stockpulse.ai.vivek_bhalke.strategy;

import main.java.com.stockpulse.ai.vivek_bhalke.strategy.impl.RuleBasedPricingStrategy;
import main.java.com.stockpulse.ai.vivek_bhalke.strategy.impl.AIPricingStrategy;
import main.java.com.stockpulse.ai.vivek_bhalke.strategy.impl.RuleBasedReorderStrategy;
import main.java.com.stockpulse.ai.vivek_bhalke.strategy.impl.AIReorderStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StrategyFactory {
    
    private final RuleBasedPricingStrategy ruleBasedPricing;
    private final AIPricingStrategy aiPricing;
    private final RuleBasedReorderStrategy ruleBasedReorder;
    private final AIReorderStrategy aiReorder;
    
    @Value("${commerce.strategy.pricing:RULE_BASED}")
    private String pricingStrategyConfig;
    
    @Value("${commerce.strategy.reorder:RULE_BASED}")
    private String reorderStrategyConfig;
    
    public StrategyFactory(RuleBasedPricingStrategy ruleBasedPricing, 
                          AIPricingStrategy aiPricing,
                          RuleBasedReorderStrategy ruleBasedReorder,
                          AIReorderStrategy aiReorder) {
        this.ruleBasedPricing = ruleBasedPricing;
        this.aiPricing = aiPricing;
        this.ruleBasedReorder = ruleBasedReorder;
        this.aiReorder = aiReorder;
    }
    
    public PricingStrategy getActivePricingStrategy() {
        if ("AI".equalsIgnoreCase(pricingStrategyConfig)) {
            return aiPricing;
        }
        return ruleBasedPricing;
    }
    
    public ReorderStrategy getActiveReorderStrategy() {
        if ("AI".equalsIgnoreCase(reorderStrategyConfig)) {
            return aiReorder;
        }
        return ruleBasedReorder;
    }
}