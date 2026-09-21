# PS012 Architecture

```text
                         +----------------------+
                         | Student / Admin      |
                         +----------+-----------+
                                    |
                                    v
                         +----------------------+
                         | API Gateway :8080     |
                         | JWT validation        |
                         | Load-balanced routing |
                         +----------+-----------+
                                    |
                   +----------------+----------------+
                   |                |                |
                   v                v                v
             Auth Service      Exam Service     Submission Service
                :8081             :8082              :8083
                   |                |                 |
                   v                v                 | OpenFeign
               auth_schema     exam_schema            +------+
                                                       |      |
                                                       v      |
                                                Evaluation Service
                                                       :8084
                                                       |
                                                       v
                                                 evaluation_schema

                         +----------------------+
                         | Eureka :8761         |
                         | Service Discovery    |
                         +----------------------+
```

## Service boundaries

### Auth Service
Identity, registration, login, BCrypt password hashing and JWT creation.

### Exam Service
Exam lifecycle, question bank, publishing and protected internal answer-key/runtime APIs.

### Submission Service
Time-bounded exam attempts, answer ingestion, duplicate prevention and orchestration of evaluation.

### Evaluation Service
Deterministic automated scoring and result persistence.

## Security
External requests enter through the gateway. The gateway validates JWTs for protected routes. Business services independently validate JWTs as defense in depth. Service-to-service calls use a JWT with role `SERVICE`.

## Load balancing
Gateway routes use `lb://SERVICE-NAME`. Eureka may register multiple instances of the same service. Spring Cloud LoadBalancer can distribute requests between available instances.

## High concurrency considerations
- Stateless services
- Independent service schemas
- Database connection pooling
- Database indexes/unique constraints
- No server-side session state
- Backend-enforced exam deadline
- Idempotent evaluation by submission ID
- Horizontal service instances through Eureka + LoadBalancer
