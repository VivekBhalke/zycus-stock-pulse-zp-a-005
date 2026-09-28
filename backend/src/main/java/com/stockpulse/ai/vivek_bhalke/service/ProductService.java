
package main.java.com.stockpulse.ai.vivek_bhalke.service;

import main.java.com.stockpulse.ai.vivek_bhalke.entity.*;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    
    /**
     * Create a new product with initial stock and price
     */
    Product createProduct(Product product);
    
    /**
     * Get products with optional filtering by status and category
     */
    List<Product> getProducts(ProductStatus status, String category);
    
    /**
     * Get product by ID
     */
    Product getProductById(String id);
    
    /**
     * Update stock level and trigger agentic loop if below reorder threshold
     */
    Product updateStock(String id, Integer newStockLevel);
    
    /**
     * Process order (decrement stock, bump demand velocity) and trigger loops
     */
    Product processOrder(String id, Integer quantity);
    
    /**
     * Generate on-demand pricing suggestion
     */
    PricingSuggestion generatePricingSuggestion(String id);
    
    /**
     * Generate on-demand reorder suggestion
     */
    ReorderSuggestion generateReorderSuggestion(String id);
    
    /**
     * Update pricing suggestion status and update product price if approved
     */
    PricingSuggestion updatePricingSuggestion(Long suggestionId, SuggestionStatus status);
    
    /**
     * Update reorder suggestion status and update stock if approved
     */
    ReorderSuggestion updateReorderSuggestion(Long suggestionId, SuggestionStatus status);
    
    /**
     * Get pending pricing suggestions for a product
     */
    List<PricingSuggestion> getPendingPricingSuggestions(String productId);
    
    /**
     * Get pending reorder suggestions for a product
     */
    List<ReorderSuggestion> getPendingReorderSuggestions(String productId);
    
    /**
     * Check if stock is below reorder threshold and trigger suggestions
     */
    void checkAndTriggerLowStockSuggestions(String productId);
    
    /**
     * Check if demand velocity spiked and trigger suggestions
     */
    void checkAndTriggerDemandSpikeSuggestions(String productId);
}

