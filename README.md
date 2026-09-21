# PS012 EduAssess Global

## Updated baseline

- Java 21
- Spring Boot 3.4.12
- Spring Cloud 2024.0.3
- Spring Cloud Netflix Eureka 4.2.3 via the Spring Cloud BOM
- PostgreSQL

The Eureka clients are configured to use Spring Cloud's non-Jersey HTTP transport (`eureka.client.jersey.enabled=false`) to avoid the `TransportClientFactories` startup failure seen with an inconsistent Eureka client classpath.

### Local startup order

1. PostgreSQL
2. Eureka Server (`8761`)
3. Auth Service (`8081`)
4. Exam Service (`8082`)
5. Submission Service (`8083`)
6. Evaluation Service (`8084`)
7. API Gateway (`8080`)

After importing the root folder as an Existing Maven Project in STS, run **Maven > Update Project** and then **Maven clean/install** before launching the services.

# PS012 - EduAssess Global
## Distributed Online Examination & Automated Evaluation Engine

A Java 21 / Spring Boot microservices implementation for the PS012 continuous evaluation project.

### Services
- Eureka Server: 8761
- API Gateway: 8080
- Auth Service: 8081
- Exam Service: 8082
- Submission Service: 8083
- Evaluation Service: 8084
- Common Security: shared JWT library

### Core flow
Student/Admin -> API Gateway -> Eureka-discovered microservices.

Submission Service communicates with Evaluation Service using OpenFeign and service-to-service JWT.
Submission Service also uses a service JWT to obtain the protected answer key from Exam Service.

### Local prerequisites
- JDK 21
- Maven 3.9+
- PostgreSQL 14+
- STS 4 / Spring Tools
- Optional: Docker Desktop

### PostgreSQL setup
Create a database named `eduassess` and run `database/init-db.sql`.

Default local credentials:
- host: localhost
- port: 5432
- database: eduassess
- user: postgres
- password: postgres

If yours are different, change the environment variables or `application.yml` values.

### Run in STS
Import the root folder as an Existing Maven Project. Maven should discover all modules.

Start in this order:
1. EurekaServerApplication
2. AuthServiceApplication
3. ExamServiceApplication
4. SubmissionServiceApplication
5. EvaluationServiceApplication
6. ApiGatewayApplication

Then open:
- http://localhost:8761
- http://localhost:8080

### Demo accounts
Admin:
- email: admin@eduassess.com
- password: Admin@123

Student:
- email: student@eduassess.com
- password: Student@123

The auth service seeds these accounts when the auth database is empty.

### API base
Use the gateway for normal client requests:
http://localhost:8080

Examples:
POST /auth/login
GET /exams
POST /exams
POST /submissions/exams/{examId}/start
POST /submissions/{submissionId}/submit
GET /evaluations/mine

### Important security note
The JWT secret in application.yml is a development-only default. For deployment, set a long random `JWT_SECRET` environment variable consistently across all services.

### Docker
Run:
docker compose up --build

This starts PostgreSQL, Eureka, all services and the gateway.

### Testing
Run from the root:
mvn test

The project includes unit tests for the automated evaluation logic. Add further integration/load tests with Postman/JMeter as part of the evaluation evidence.

### Rubric evidence
1. Problem analysis: README + docs
2. Microservices: 4 business services + Eureka
3. JWT: Spring Security + signed, expiring JWT + roles
4. API Gateway: Gateway routing + Eureka load-balanced `lb://` routes + JWT filter
5. LinkedIn article: use the architecture and test evidence from `docs/linkedIn-article-outline.md`
