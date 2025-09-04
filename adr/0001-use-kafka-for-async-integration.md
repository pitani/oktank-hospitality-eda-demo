
# ADR 0001: Use Kafka for Async Integration
**Decision**: Use Kafka to decouple services and enable async workflows.  
**Context**: Reservation creation triggers multiple downstream actions with different SLAs.  
**Consequences**: Services can evolve independently; must manage schema versioning & DLQs.
