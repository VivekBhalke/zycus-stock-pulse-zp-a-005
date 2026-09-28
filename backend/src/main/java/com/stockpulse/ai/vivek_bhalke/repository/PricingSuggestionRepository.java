package com.stockpulse.ai.vivek_bhalke.repository;

import com.stockpulse.ai.vivek_bhalke.entity.PricingSuggestion;
import com.stockpulse.ai.vivek_bhalke.entity.SuggestionStatus;
import com.stockpulse.ai.vivek_bhalke.entity.TriggerReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PricingSuggestionRepository extends JpaRepository<PricingSuggestion, Long> {
    
    List<PricingSuggestion> findByProductId(String productId);
    
    List<PricingSuggestion> findByProductIdAndStatus(String productId, SuggestionStatus status);
    
    List<PricingSuggestion> findByStatus(SuggestionStatus status);
    
    List<PricingSuggestion> findByProductIdAndTriggerReasonAndStatus(
        String productId, 
        TriggerReason triggerReason, 
        SuggestionStatus status
    );
    
    List<PricingSuggestion> findByTriggerReason(TriggerReason triggerReason);
}