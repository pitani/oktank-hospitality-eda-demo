
# ADR 0003: Outbox vs Transactional Producer
**Decision**: For demo, simple transactional producer; for production, adopt Outbox pattern.  
**Context**: Avoid dual-write inconsistency between DB and Kafka.  
**Consequences**: Extra relay component, but stronger reliability guarantees.
