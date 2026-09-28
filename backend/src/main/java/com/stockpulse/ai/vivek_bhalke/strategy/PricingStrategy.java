package main.java.com.stockpulse.ai.vivek_bhalke.strategy;

import main.java.com.stockpulse.ai.vivek_bhalke.entity.Product;
import main.java.com.stockpulse.ai.vivek_bhalke.dto.PricingAdvice;
import main.java.com.stockpulse.ai.vivek_bhalke.dto.ReorderAdvice;
import java.math.BigDecimal;

public interface PricingStrategy {
    PricingAdvice calculatePricingAdvice(Product product);
}