# 1. Record In-Scope Decisions for Sprint 1

Date: 2026-09-28

## Status

Accepted

## Context

Building a reactive commerce advisor system that automatically detects inventory thresholds and demand velocity spikes to generate AI-powered pricing and reorder suggestions. Need to clearly define what is in-scope for Sprint 1 to ensure focused delivery while maintaining extensibility for future sprints.

The system must handle the critical path: inventory-signal → AI recommendation → human approval loop without requiring manual intervention to trigger insights.

## Decision

The following components and features are in-scope for Sprint 1 implementation:

### Core Domain Model
**In Scope:** Product, InventorySnapshot, PricingSuggestion, ReorderSuggestion entities with explicit state machines
**Reasoning:** These form the fundamental data structures needed to represent the business domain. Explicit state machines ensure predictable behavior and clear audit trails for all commercial decisions.

### Pluggable Strategy Interface
**In Scope:** Commerce strategy interface with rule-based and AI-powered implementations for pricing decisions
**Reasoning:** Enables runtime switching between strategies without system restart, providing flexibility to fallback to rule-based approaches when AI is unavailable while maintaining consistent interfaces for future extensions.

### AI Commerce Advisor Core
**In Scope:** LLM integration for pricing and reorder recommendations with two distinct prompt strategies
**Reasoning:** Central value proposition requires contextual AI reasoning. Separating inventory-low vs demand-spike prompts ensures model receives appropriate context for optimal decision quality.
<!-- 
### Agentic Recommendation Loop
**In Scope:** Asynchronous event-driven suggestion generation triggered by inventory/demand signals
**Reasoning:** Eliminates manual dashboard monitoring by automating the detection-to-proposal workflow. Async processing maintains responsive APIs while ensuring merchandising receives timely recommendations. -->

### Merchandising Console Foundation
**In Scope:** Basic UI displaying pending suggestions with AI reasoning and approval controls
**Reasoning:** Critical touchpoint for human oversight. Minimal viable interface proves the end-to-end workflow while avoiding UI polish that could compromise core functionality delivery.

### REST API Endpoints
**In Scope:** Product CRUD, stock updates, order simulation, and suggestion management endpoints
**Reasoning:** Enables system integration and provides clear interfaces for both external triggers (inventory changes) and internal operations (suggestion acceptance).

### Database Persistence Layer
**In Scope:** JPA/Hibernate entity mapping with repository patterns
**Reasoning:** Ensures data durability and provides foundation for reporting, audit trails, and future analytics extensions while maintaining technology flexibility.

## Consequences

### Positive
- Focused scope delivers core value proposition: automatic commercial recommendations
- Modular architecture supports Sprint 2 extensions (competitor pricing, automated POs)
- Clear separation between detection logic, recommendation engine, and approval workflow
- Runtime strategy switching provides resilience against AI service outages
- Explicit state management enables reliable audit trails

### Negative
- Limited to basic UI presentation - advanced visualization delayed to future sprints
- No automated execution of approved suggestions (manual approval required)
- Single LLM provider integration initially (multi-provider support in future)
- Historical analysis capabilities minimal (focused on current state triggers)

### Neutral
- Entity model designed for extension but initial implementation covers core use cases
- Strategy pattern adds slight complexity but enables future A/B testing capabilities
- Async processing introduces eventual consistency considerations but improves user experience

## Additional Notes

This scope prioritizes system reliability and core workflow integrity over feature completeness, establishing a solid foundation for iterative enhancement in subsequent sprints while delivering measurable business value immediately.