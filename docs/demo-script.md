# PS012 5-minute Demo Script

1. Open Eureka at http://localhost:8761 and show all registered services.
2. Open http://localhost:8080 for the EduAssess UI.
3. Login as admin and create a draft exam.
4. Add three MCQ questions and publish.
5. Login as student and start the published exam.
6. Submit answers before the deadline.
7. Show the returned score and percentage.
8. Use Postman to demonstrate a protected request without JWT -> 401.
9. Use a STUDENT token on an ADMIN endpoint -> 403.
10. Run a second instance of Exam Service on another port and show both instances in Eureka; send requests through the gateway to demonstrate `lb://EXAM-SERVICE`.

Explain that PostgreSQL schemas are isolated by service and that Submission Service invokes Evaluation Service using OpenFeign with service JWT authentication.
