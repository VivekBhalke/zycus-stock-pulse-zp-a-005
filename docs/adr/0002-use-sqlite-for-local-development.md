````markdown
# 2. Use SQLite for Local Development

Date: 2026-09-28

## Status

Accepted

## Context

For local development and rapid prototyping of the reactive commerce advisor system, we need a database solution that minimizes setup time while maintaining compatibility with production-grade SQL databases. The team needs to focus on implementing core business logic and agentic recommendation loops rather than database administration.

Traditional database setups (PostgreSQL, MySQL) require:
- Installation and configuration time
- Port management and service conflicts
- Schema migration setup
- Connection pooling configuration
- Docker container management overhead

## Decision

We will use SQLite as the local development database for the following reasons:

1. **Zero Setup Time**: SQLite requires no separate server process or system-wide installation
2. **File-Based Storage**: Single `.db` file that can be version controlled or shared
3. **Full SQL Compliance**: Supports most standard SQL features needed for our entity model
4. **Easy Testing**: Simple to reset/clean between test runs
5. **Production Compatibility**: SQL syntax compatibility with PostgreSQL/H2 (used in seed script)

## Implementation Details

### Entity Mapping Approach
All entities from the in-scope requirements have been implemented in:
`backend/src/main/java/com/stockpulse/ai/vivek_bhalke/entity/`

Entities include:
- Product (core inventory item with pricing)
- InventorySnapshot (historical stock tracking)
- PricingSuggestion & ReorderSuggestion (commercial recommendations)
- Supporting configuration entities for strategy management

### SQLite Configuration
```yaml
# application.yml
spring:
  datasource:
    url: jdbc:sqlite:stockpulse-dev.db
    driver-class-name: org.sqlite.JDBC
  jpa:
    database-platform: org.hibernate.community.dialect.SQLiteDialect
    hibernate:
      ddl-auto: update
````

## Consequences

### Positive

- Immediate development start without database setup delays
- Simplified onboarding for new team members
- Easy database reset for testing scenarios
- Reduced resource consumption during development
- Direct file access enables easy backup/restore

### Negative

- Not suitable for production workloads
- Limited concurrency handling compared to server-based databases
- Some advanced SQL features may differ from PostgreSQL
- File locking issues possible in collaborative environments

### Neutral

- Seamless transition to PostgreSQL in production via configuration change
- Same JPA/Hibernate entity annotations work unchanged
- Migration scripts can be developed and tested locally

## Follow-up Actions

1. Create `stockpulse-dev.db` in project root
2. Implement JPA repositories for all entities
3. Add H2/PostgreSQL profile for production-like testing
4. Document production database migration path

This decision prioritizes developer velocity for Sprint 1 delivery while maintaining architectural flexibility for future scaling.
