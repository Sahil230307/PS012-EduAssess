# Testing & Evaluation Evidence

## Unit tests
Run `mvn test`. The repository contains:
- JWT generation/parsing test.
- Automated evaluation score/percentage test.

## Integration test flow (Postman)
1. Start Eureka and all services.
2. Login as admin.
3. Create a draft exam.
4. Add at least three questions.
5. Publish the exam.
6. Login as student.
7. Start the exam.
8. Submit answers.
9. Verify Evaluation Service generated the result.
10. Call admin result/submission endpoints.

## Security evidence
- Call a protected endpoint without Authorization -> 401.
- Login and use a valid JWT -> 200.
- Use a STUDENT JWT against an ADMIN endpoint -> 403.
- Expired/invalid JWT -> 401.

## Load testing
Use Apache JMeter or another load-testing tool to send concurrent requests to:
`GET /exams`
and a controlled set of authenticated submission requests.

Capture:
- concurrent users
- throughput
- average response time
- error percentage
- screenshots of multiple Eureka service instances

Do not run destructive high-concurrency tests against a production database.
