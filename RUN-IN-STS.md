# Run in Spring Tool Suite (STS)

1. Extract the ZIP.
2. In STS: File -> Import -> Maven -> Existing Maven Projects.
3. Select the extracted `PS012-EduAssess` folder.
4. Finish and wait for Maven dependencies to download.
5. Create PostgreSQL database `eduassess`.
6. Ensure PostgreSQL username/password match the defaults in each `application.yml`, or set environment variables.
7. Run the six application classes in this order:
   - `EurekaServerApplication`
   - `AuthServiceApplication`
   - `ExamServiceApplication`
   - `SubmissionServiceApplication`
   - `EvaluationServiceApplication`
   - `ApiGatewayApplication`
8. Open `http://localhost:8761` and verify registrations.
9. Open `http://localhost:8080` and use the web UI.

## If a service port is already occupied
Run the service with:
`--server.port=8092`

For an additional Exam Service instance, use port 8092. Eureka will register both instances and Gateway's `lb://EXAM-SERVICE` route can load-balance between them.

## If your PostgreSQL password is not postgres
Either edit:
`spring.datasource.password`
in each service, or create an environment variable:
`DB_PASSWORD=<your-password>`

The application reads:
`${DB_PASSWORD:postgres}`.
