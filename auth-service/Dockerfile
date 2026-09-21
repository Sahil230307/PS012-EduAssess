FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
COPY common-security common-security
COPY auth-service auth-service
COPY api-gateway api-gateway
COPY auth-service auth-service
COPY exam-service exam-service
COPY submission-service submission-service
COPY evaluation-service evaluation-service
COPY eureka-server eureka-server
RUN mvn -q -pl auth-service -am clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/auth-service/target/auth-service-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
