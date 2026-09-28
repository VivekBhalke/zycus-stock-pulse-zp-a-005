package com.stockpulse.ai.vivek_bhalke.controller;

import com.stockpulse.ai.vivek_bhalke.strategy.StrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/strategies")
@CrossOrigin(origins = "*")
public class StrategyController {
    
    @Autowired
    private StrategyFactory strategyFactory;
    
    /**
     * GET /strategies/active - Get currently active strategy
     */
    @GetMapping("/active")
    public ResponseEntity<String> getActiveStrategy() {
        // This is a simplified approach - in reality, you might want to expose this differently
        return ResponseEntity.ok("Active strategy: RuleBased (default)");
    }
    
    /**
     * POST /strategies/switch - Switch active strategy
     */
    @PostMapping("/switch")
    public ResponseEntity<String> switchStrategy(@RequestParam String strategy) {
        try {
            strategyFactory.setActiveStrategyConfig(strategy);
            return ResponseEntity.ok("Successfully switched to strategy: " + strategy);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to switch strategy: " + e.getMessage());
        }
    }
}