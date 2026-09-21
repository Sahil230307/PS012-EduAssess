# Deployment Guide

## Option A - Docker on a Linux VM
1. Install Docker and Docker Compose.
2. Copy this repository to the VM.
3. Set a strong `JWT_SECRET`.
4. Run `docker compose up -d --build`.
5. Expose port 8080 through the VM firewall.
6. Open `http://<server-ip>:8080`.

For a production deployment, put HTTPS/TLS in front of the gateway with a reverse proxy and do not expose PostgreSQL publicly.

## Option B - Cloud container platform
The application is containerized and can be deployed as six application containers plus PostgreSQL. Configure the environment variables in each service:
- DB_HOST
- DB_PORT
- DB_NAME
- DB_USER
- DB_PASSWORD
- JWT_SECRET
- EUREKA_URL

Use the platform's private networking so service containers can reach Eureka and PostgreSQL without public exposure.

## Production improvements
- HTTPS
- Secret manager
- Managed PostgreSQL
- Centralized logging
- Actuator health checks
- Resilience4j circuit breakers
- Redis/Kafka for very large scale workloads
- Metrics with Prometheus/Grafana
