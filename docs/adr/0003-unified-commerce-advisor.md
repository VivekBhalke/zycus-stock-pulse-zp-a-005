# 3. Use Unified Commerce Advisor with Runtime Strategy Toggling

Date: 2026-09-28

## Status

Accepted

## Context

For the reactive commerce advisor system, we need to implement a flexible strategy pattern that allows switching between rule-based and AI-powered approaches for generating pricing and reorder suggestions. The system must support runtime switching without server restarts while maintaining clean architectural boundaries.

Two architectural approaches were considered:
1. Separate interfaces for pricing and reorder strategies
2. Unified CommerceAdvisor interface that handles both pricing and reorder decisions together

Additionally, we need to support runtime strategy switching to enable seamless transitions between rule-based and AI-powered approaches without requiring server restarts.

## Decision

We will implement a unified CommerceAdvisor interface with runtime strategy toggling for the following reasons:

### Guarantees Zero-Restart Toggling
Bypasses rigid boot-time injections like @Value by using a dynamic state lookup, allowing the frontend to switch active strategies instantaneously without resetting the server. This provides immediate feedback for merchandisers testing different strategies.

### Pre-empts AI and Pipeline Alignment
Combines pricing and reordering into a single execution pass, mirroring how the upcoming Sprint 2 AI models naturally process product data context together. This unified approach reduces redundant data fetching and processing while ensuring coherent commercial decisions.

### Ensures True "Plug-and-Play" Scalability
Streamlines the architecture so adding future modules (like the CompetitorAwareStrategy from Sprint 2) requires writing just one single implementation class rather than splitting and altering multiple interfaces. New strategies can be added by simply implementing the CommerceAdvisor interface and registering the bean.

## Implementation Details

### Unified Interface
```java
public interface CommerceAdvisor {
    CommerceAdvice calculateAdvice(Product product);
}
```

### Runtime Toggling Mechanism
```java
@Component
public class StrategyFactory {
    private final RuleBasedCommerceAdvisor ruleBased;
    private final AICommerceAdvisor aiAdvisor;
    private String activeStrategyConfig = "RULE_BASED";
    
    public void setActiveStrategyConfig(String strategy) {
        this.activeStrategyConfig = strategy;
    }
    
    public CommerceAdvisor getActiveStrategy() {
        if ("AI".equalsIgnoreCase(activeStrategyConfig)) {
            return aiAdvisor;
        }
        return ruleBased;
    }
}
```

### Strategy Implementations
1. **RuleBasedCommerceAdvisor** - Implements deterministic business rules for both pricing and reorder decisions
2. **AICommerceAdvisor** - Integrates with LLM services (placeholder for Sprint 2)

## Consequences

### Positive
- Instant strategy switching without server restarts improves operational agility
- Single execution context for AI models reduces latency and resource consumption
- Simplified extension model accelerates future feature development
- Consistent API contracts between HTTP endpoints and async agentic loops
- Reduced code duplication and maintenance overhead

### Negative
- Slightly more complex unified advice object compared to separate pricing/reorder objects
- Initial learning curve for developers unfamiliar with the unified approach
- Potential for coupling between pricing and reorder logic (mitigated by clean implementation)

### Neutral
- Maintains backward compatibility with existing service interfaces
- Supports gradual migration from separate strategy approach
- Enables A/B testing of strategies at runtime

## Follow-up Actions

1. Implement RuleBasedCommerceAdvisor with current business rules
2. Create placeholder AICommerceAdvisor for Sprint 2 integration
3. Update service layer to use unified CommerceAdvisor approach
4. Add API endpoint for strategy configuration
5. Document strategy switching for merchandising team