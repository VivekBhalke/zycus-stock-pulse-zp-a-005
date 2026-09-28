package main.java.com.stockpulse.ai.vivek_bhalke.strategy;

import main.java.com.stockpulse.ai.vivek_bhalke.entity.Product;
import main.java.com.stockpulse.ai.vivek_bhalke.dto.CommerceAdvice;

public interface CommerceAdvisor {
    CommerceAdvice calculateAdvice(Product product);
}