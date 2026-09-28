
package main.java.com.stockpulse.ai.vivek_bhalke.service;

import main.java.com.stockpulse.ai.vivek_bhalke.entity.*;
import main.java.com.stockpulse.ai.vivek_bhalke.repository.*;
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
    
    @Override
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
            // Call AI service or rule-based logic
            BigDecimal suggestedPrice = calculateSuggestedPrice(product);
            String reasoning = generatePricingReasoning(product, suggestedPrice);
            
            PricingSuggestion suggestion = new PricingSuggestion();
            suggestion.setProductId(id);
            suggestion.setSuggestedPrice(suggestedPrice);
            suggestion.setConfidenceScore(0.85);
            suggestion.setReasoning(reasoning);
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
            // Call AI service or rule-based logic
            Integer suggestedQuantity = calculateSuggestedReorderQuantity(product);
            String reasoning = generateReorderReasoning(product, suggestedQuantity);
            
            ReorderSuggestion suggestion = new ReorderSuggestion();
            suggestion.setProductId(id);
            suggestion.setSuggestedQuantity(suggestedQuantity);
            suggestion.setConfidenceScore(0.80);
            suggestion.setReasoning(reasoning);
            suggestion.setTriggerReason(TriggerReason.MANUAL);
            suggestion.setStatus(SuggestionStatus.PENDING);
            suggestion.setCreatedAt(LocalDateTime.now());
            suggestion.setUpdatedAt(LocalDateTime.now());
            
            return reorderSuggestionRepository.save(suggestion);
        }
        return null;
    }
    
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
    public List<PricingSuggestion> getPendingPricingSuggestions(String productId) {
        return pricingSuggestionRepository.findByProductIdAndStatus(productId, SuggestionStatus.PENDING);
    }
    
    @Override
    public List<ReorderSuggestion> getPendingReorderSuggestions(String productId) {
        return reorderSuggestionRepository.findByProductIdAndStatus(productId, SuggestionStatus.PENDING);
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
            
            if (existingPricingSuggestions.isEmpty()) {
                // Generate new pricing suggestion
                generatePricingSuggestion(productId);
            }
            
            if (existingReorderSuggestions.isEmpty()) {
                // Generate new reorder suggestion
                generateReorderSuggestion(productId);
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
                    // Generate new pricing suggestion only for demand spikes
                    generatePricingSuggestion(productId);
                }
            }
        }
    }
    
    // Helper methods (would be more sophisticated in real implementation)
    private BigDecimal calculateSuggestedPrice(Product product) {
        // Placeholder for AI or rule-based pricing logic
        return product.getCurrentPrice().multiply(new BigDecimal("1.1")); // 10% increase example
    }
    
    private Integer calculateSuggestedReorderQuantity(Product product) {
        // Placeholder for AI or rule-based reorder logic
        return Math.max(50, product.getReorderThreshold() * 3); // Example calculation
    }
    
    private String generatePricingReasoning(Product product, BigDecimal suggestedPrice) {
        // Placeholder for AI-generated reasoning
        return "Based on current stock levels (" + product.getStockLevel() + ") and demand velocity (" + 
               product.getDemandVelocity() + "), suggesting price adjustment from $" + 
               product.getCurrentPrice() + " to $" + suggestedPrice + ".";
    }
    
    private String generateReorderReasoning(Product product, Integer suggestedQuantity) {
        // Placeholder for AI-generated reasoning
        return "Based on current stock levels (" + product.getStockLevel() + ") and reorder threshold (" + 
               product.getReorderThreshold() + "), suggesting reorder quantity of " + suggestedQuantity + " units.";
    }
    
    private boolean isDemandVelocitySpike(Product product) {
        // Placeholder for demand spike detection logic
        // Would compare with category averages and configured multipliers
        return product.getDemandVelocity() > 10; // Simple example
    }
}