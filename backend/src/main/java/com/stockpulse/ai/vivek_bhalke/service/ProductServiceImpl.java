package main.java.com.stockpulse.ai.vivek_bhalke.service;

import main.java.com.stockpulse.ai.vivek_bhalke.entity.*;
import main.java.com.stockpulse.ai.vivek_bhalke.repository.*;
import main.java.com.stockpulse.ai.vivek_bhalke.strategy.StrategyFactory;
import main.java.com.stockpulse.ai.vivek_bhalke.strategy.CommerceAdvisor;
import main.java.com.stockpulse.ai.vivek_bhalke.dto.CommerceAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private PricingSuggestionRepository pricingSuggestionRepository;
    
    @Autowired
    private ReorderSuggestionRepository reorderSuggestionRepository;
    
    @Autowired
    private StrategyFactory strategyFactory;
    
    @Override
    
    @Override
    public Product updateStock(String id, Integer newStockLevel) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setStockLevel(newStockLevel);
            product.setUpdatedAt(LocalDateTime.now());
            Product updatedProduct = productRepository.save(product);
            
            // Check if stock is below reorder threshold
            if (newStockLevel < product.getReorderThreshold()) {
                checkAndTriggerLowStockSuggestions(id);
            }
            
            return updatedProduct;
        }
        return null;
    }
    
    @Override
    public Product processOrder(String id, Integer quantity) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            int newStockLevel = product.getStockLevel() - quantity;
            int newDemandVelocity = product.getDemandVelocity() + 1;
            
            product.setStockLevel(newStockLevel);
            product.setDemandVelocity(newDemandVelocity);
            product.setUpdatedAt(LocalDateTime.now());
            
            Product updatedProduct = productRepository.save(product);
            
            // Check for triggers
            checkAndTriggerLowStockSuggestions(id);
            checkAndTriggerDemandSpikeSuggestions(id);
            
            return updatedProduct;
        }
        return null;
    }
    
    @Override
    public PricingSuggestion generatePricingSuggestion(String id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            // Use the active unified commerce advisor
            CommerceAdvisor commerceAdvisor = strategyFactory.getActiveStrategy();
            CommerceAdvice commerceAdvice = commerceAdvisor.calculateAdvice(product);
            
            PricingSuggestion suggestion = new PricingSuggestion();
            suggestion.setProductId(id);
            suggestion.setSuggestedPrice(commerceAdvice.getSuggestedPrice());
            suggestion.setConfidenceScore(commerceAdvice.getConfidenceScore());
    
    @Override
    public PricingSuggestion updatePricingSuggestion(Long suggestionId, SuggestionStatus status) {
        PricingSuggestion suggestion = pricingSuggestionRepository.findById(suggestionId).orElse(null);
        if (suggestion != null) {
            suggestion.setStatus(status);
            suggestion.setUpdatedAt(LocalDateTime.now());
            
            PricingSuggestion updatedSuggestion = pricingSuggestionRepository.save(suggestion);
            
            // If approved, update product price
            if (status == SuggestionStatus.APPROVED) {
                Product product = productRepository.findById(suggestion.getProductId()).orElse(null);
                if (product != null) {
                    product.setCurrentPrice(suggestion.getSuggestedPrice());
                    product.setUpdatedAt(LocalDateTime.now());
                    productRepository.save(product);
                }
            }
            
            return updatedSuggestion;
        }
        return null;
    }
    
    @Override
    public ReorderSuggestion updateReorderSuggestion(Long suggestionId, SuggestionStatus status) {
        ReorderSuggestion suggestion = reorderSuggestionRepository.findById(suggestionId).orElse(null);
        if (suggestion != null) {
            suggestion.setStatus(status);
            suggestion.setUpdatedAt(LocalDateTime.now());
            
            ReorderSuggestion updatedSuggestion = reorderSuggestionRepository.save(suggestion);
            
            // If approved, update stock level (simulate inbound shipment)
            if (status == SuggestionStatus.APPROVED) {
                Product product = productRepository.findById(suggestion.getProductId()).orElse(null);
                if (product != null) {
                    int newStockLevel = product.getStockLevel() + suggestion.getSuggestedQuantity();
                    product.setStockLevel(newStockLevel);
                    product.setUpdatedAt(LocalDateTime.now());
                    productRepository.save(product);
                }
            }
            
            return updatedSuggestion;
        }
        return null;
    }
    
    
    @Override
    public void checkAndTriggerLowStockSuggestions(String productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null && product.getStockLevel() < product.getReorderThreshold()) {
            // Check if suggestion already exists
            List<PricingSuggestion> existingPricingSuggestions = 
                pricingSuggestionRepository.findByProductIdAndTriggerReasonAndStatus(
                    productId, TriggerReason.INVENTORY_LOW, SuggestionStatus.PENDING);
            
            List<ReorderSuggestion> existingReorderSuggestions = 
                reorderSuggestionRepository.findByProductIdAndTriggerReasonAndStatus(
                    productId, TriggerReason.INVENTORY_LOW, SuggestionStatus.PENDING);
            
            // Use the active unified commerce advisor
            CommerceAdvisor commerceAdvisor = strategyFactory.getActiveStrategy();
            CommerceAdvice commerceAdvice = commerceAdvisor.calculateAdvice(product);
            
            if (existingPricingSuggestions.isEmpty()) {
                // Generate new pricing suggestion using the active strategy
                PricingSuggestion pricingSuggestion = new PricingSuggestion();
                pricingSuggestion.setProductId(productId);
                pricingSuggestion.setSuggestedPrice(commerceAdvice.getSuggestedPrice());
                pricingSuggestion.setConfidenceScore(commerceAdvice.getConfidenceScore());
                pricingSuggestion.setReasoning(commerceAdvice.getReasoning());
                pricingSuggestion.setTriggerReason(TriggerReason.INVENTORY_LOW);
                pricingSuggestion.setStatus(SuggestionStatus.PENDING);
                pricingSuggestion.setCreatedAt(LocalDateTime.now());
                pricingSuggestion.setUpdatedAt(LocalDateTime.now());
                
                pricingSuggestionRepository.save(pricingSuggestion);
            }
            
            if (existingReorderSuggestions.isEmpty()) {
                // Generate new reorder suggestion using the active strategy
                ReorderSuggestion reorderSuggestion = new ReorderSuggestion();
                reorderSuggestion.setProductId(productId);
                reorderSuggestion.setSuggestedQuantity(commerceAdvice.getSuggestedReorderQuantity());
                reorderSuggestion.setConfidenceScore(commerceAdvice.getConfidenceScore());
                reorderSuggestion.setReasoning(commerceAdvice.getReasoning());
                reorderSuggestion.setTriggerReason(TriggerReason.INVENTORY_LOW);
                reorderSuggestion.setStatus(SuggestionStatus.PENDING);
                reorderSuggestion.setCreatedAt(LocalDateTime.now());
                reorderSuggestion.setUpdatedAt(LocalDateTime.now());
                
                reorderSuggestionRepository.save(reorderSuggestion);
            }
        }
    }
    
    @Override
    public void checkAndTriggerDemandSpikeSuggestions(String productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            // Check if demand velocity spiked (3x category average or configured threshold)
            // This would involve comparing with category averages from DemandThresholdConfig
            boolean isSpike = isDemandVelocitySpike(product);
            
            if (isSpike) {
                // Check if suggestion already exists
                List<PricingSuggestion> existingPricingSuggestions = 
                    pricingSuggestionRepository.findByProductIdAndTriggerReasonAndStatus(
                        productId, TriggerReason.DEMAND_SPIKE, SuggestionStatus.PENDING);
                
                if (existingPricingSuggestions.isEmpty()) {
                    // Use the active unified commerce advisor
                    CommerceAdvisor commerceAdvisor = strategyFactory.getActiveStrategy();
                    CommerceAdvice commerceAdvice = commerceAdvisor.calculateAdvice(product);
                    
                    // Generate new pricing suggestion only for demand spikes using the active strategy
                    PricingSuggestion pricingSuggestion = new PricingSuggestion();
                    pricingSuggestion.setProductId(productId);
                    pricingSuggestion.setSuggestedPrice(commerceAdvice.getSuggestedPrice());
                    pricingSuggestion.setConfidenceScore(commerceAdvice.getConfidenceScore());
                    pricingSuggestion.setReasoning(commerceAdvice.getReasoning());
                    pricingSuggestion.setTriggerReason(TriggerReason.DEMAND_SPIKE);
                    pricingSuggestion.setStatus(SuggestionStatus.PENDING);
                    pricingSuggestion.setCreatedAt(LocalDateTime.now());
                    pricingSuggestion.setUpdatedAt(LocalDateTime.now());
                    
                    pricingSuggestionRepository.save(pricingSuggestion);
                }
            }
        }
    }
    
    // Helper method for demand spike detection logic
    private boolean isDemandVelocitySpike(Product product) {
        // Placeholder for demand spike detection logic
        // Would compare with category averages and configured multipliers
        return product.getDemandVelocity() > 10; // Simple example
    }
}
    @Override
    public List<PricingSuggestion> getPendingPricingSuggestions(String productId) {
        return pricingSuggestionRepository.findByProductIdAndStatus(productId, SuggestionStatus.PENDING);
    }
    
    @Override
    public List<ReorderSuggestion> getPendingReorderSuggestions(String productId) {
        return reorderSuggestionRepository.findByProductIdAndStatus(productId, SuggestionStatus.PENDING);
    }
            suggestion.setReasoning(commerceAdvice.getReasoning());
            suggestion.setTriggerReason(TriggerReason.MANUAL);
            suggestion.setStatus(SuggestionStatus.PENDING);
            suggestion.setCreatedAt(LocalDateTime.now());
            suggestion.setUpdatedAt(LocalDateTime.now());
            
            return pricingSuggestionRepository.save(suggestion);
        }
        return null;
    }
    
    @Override
    public ReorderSuggestion generateReorderSuggestion(String id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            // Use the active unified commerce advisor
            CommerceAdvisor commerceAdvisor = strategyFactory.getActiveStrategy();
            CommerceAdvice commerceAdvice = commerceAdvisor.calculateAdvice(product);
            
            ReorderSuggestion suggestion = new ReorderSuggestion();
            suggestion.setProductId(id);
            suggestion.setSuggestedQuantity(commerceAdvice.getSuggestedReorderQuantity());
            suggestion.setConfidenceScore(commerceAdvice.getConfidenceScore());
            suggestion.setReasoning(commerceAdvice.getReasoning());
            suggestion.setTriggerReason(TriggerReason.MANUAL);
            suggestion.setStatus(SuggestionStatus.PENDING);
            suggestion.setCreatedAt(LocalDateTime.now());
            suggestion.setUpdatedAt(LocalDateTime.now());
            
            return reorderSuggestionRepository.save(suggestion);
        }
        return null;
    }
    public Product createProduct(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }
    
    @Override
    public List<Product> getProducts(ProductStatus status, String category) {
        if (status != null && category != null) {
            return productRepository.findByStatusAndCategory(status, category);
        } else if (status != null) {
            return productRepository.findByStatus(status);
        } else if (category != null) {
            return productRepository.findByCategory(category);
        } else {
            return productRepository.findAll();
        }
    }
    
    @Override
    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }