package main.java.com.stockpulse.ai.vivek_bhalke.strategy;

import main.java.com.stockpulse.ai.vivek_bhalke.entity.Product;
import main.java.com.stockpulse.ai.vivek_bhalke.dto.ReorderAdvice;

public interface ReorderStrategy {
    ReorderAdvice calculateReorderAdvice(Product product);
}