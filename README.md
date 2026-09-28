# StockPulse: Agentic Commerce Recommendation System

An intelligent inventory management system that automatically detects inventory thresholds and demand velocity spikes to generate AI-powered pricing and reorder suggestions.

## Project Structure

```
├── backend/        # Spring Boot backend application
├── frontend/       # React 18 merchandising console
└── docs/           # Architecture Decision Records
```

## Features

### Core Domain Model
- Product management (SKU, name, category, price, stock)
- Pricing suggestions with AI reasoning
- Reorder suggestions with lead time estimates
- State machines for product and suggestion lifecycles

### Unified Strategy Interface
- Single CommerceAdvisor interface handling both pricing and reorder decisions
- Rule-based strategy (deterministic fallback)
- AI strategy (contextual LLM-powered decisions)
- Runtime strategy switching without code changes or server restarts

### AI Commerce Advisor
- Structured context for LLM prompts
- Separate prompts for inventory-low vs demand-spike scenarios
- Validation of AI recommendations
- Graceful fallback to rule-based strategies

### Agentic Recommendation Loop
- Automatic suggestion generation on inventory signals
- Async processing for responsive APIs
- Duplicate suggestion filtering
- Silent AI failure handling with rule-based fallback

### Merchandising Console
- Dashboard view for products needing review
- Inline pricing and reorder suggestions
- Accept/Reject workflow
- Trigger reason visibility (inventory low, demand spike, manual)
- Demo simulation controls

## Getting Started

### Backend

1. Navigate to the backend directory:
```bash
cd backend
```

2. Build and run the application:
```bash
./gradlew bootRun
```

The backend will be available at http://localhost:8080

### Frontend

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```

The frontend will be available at http://localhost:3000

## API Endpoints

### Products
- `POST /api/products` - Create a new product
- `GET /api/products` - Get all products (with optional status/category filters)
- `GET /api/products/{id}` - Get a specific product
- `PATCH /api/products/{id}/stock` - Update stock level
- `POST /api/products/{id}/orders` - Process an order

### Suggestions
- `POST /api/products/{id}/suggest-pricing` - Generate pricing suggestion
- `POST /api/products/{id}/suggest-reorder` - Generate reorder suggestion
- `PATCH /api/pricing-suggestions/{id}` - Accept/reject pricing suggestion
- `PATCH /api/reorder-suggestions/{id}` - Accept/reject reorder suggestion

### Strategies
- `GET /api/strategies/active` - Get currently active strategy
- `POST /api/strategies/switch` - Switch active strategy (RULE_BASED or AI)

## Demo Paths

### Inventory Low Trigger
1. Find a product with low stock (below reorder threshold)
2. Process orders or reduce stock further
3. Observe automatic pricing and reorder suggestions

### Demand Spike Trigger
1. Find a product with normal stock but low reorder threshold
2. Process multiple orders to increase demand velocity
3. Observe demand spike triggered suggestions

## Architecture Decisions

See docs/adr/ for detailed architecture decision records covering:
- In-scope decisions for Sprint 1
- Database selection for local development
- Unified CommerceAdvisor approach with runtime strategy toggling
- LLM integration and fallback handling

## Technical Implementation Details

### Strategy Pattern
We implemented a unified CommerceAdvisor interface that combines both pricing and reorder decision-making into a single execution pass:

```java
public interface CommerceAdvisor {
    CommerceAdvice calculateAdvice(Product product);
}
```

This approach offers three key advantages:
1. **Zero-Restart Toggling**: Dynamic state lookup allows instant strategy switching
2. **AI Pipeline Alignment**: Matches how AI models naturally process product context
3. **Plug-and-Play Scalability**: Adding new strategies requires only one implementation class

### Runtime Strategy Switching
The StrategyFactory component enables switching between strategies without server restarts:

```java
@Component
public class StrategyFactory {
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

### Extensibility for Future Sprints
The architecture supports easy addition of new strategies like CompetitorAwareStrategy by simply implementing the CommerceAdvisor interface.