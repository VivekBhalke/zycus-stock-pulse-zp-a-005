package main.java.com.stockpulse.ai.vivek_bhalke.repository;

import main.java.com.stockpulse.ai.vivek_bhalke.entity.ReorderSuggestion;
import main.java.com.stockpulse.ai.vivek_bhalke.entity.SuggestionStatus;
import main.java.com.stockpulse.ai.vivek_bhalke.entity.TriggerReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReorderSuggestionRepository extends JpaRepository<ReorderSuggestion, Long> {
    
    List<ReorderSuggestion> findByProductId(String productId);
    
    List<ReorderSuggestion> findByProductIdAndStatus(String productId, SuggestionStatus status);
    
    List<ReorderSuggestion> findByStatus(SuggestionStatus status);
    
    List<ReorderSuggestion> findByProductIdAndTriggerReasonAndStatus(
        String productId, 
        TriggerReason triggerReason, 
        SuggestionStatus status
    );
    
    List<ReorderSuggestion> findByTriggerReason(TriggerReason triggerReason);
}