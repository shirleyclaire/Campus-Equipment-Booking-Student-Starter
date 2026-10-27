# Campus Equipment Booking Service
Java 17 / Spring Boot REST API for a CI/CD lab assessment.

## Build and test
`mvn clean test`

## Run
`mvn spring-boot:run` — port 8080.

## Endpoints
- `GET /api/equipment`
- `GET /api/equipment/{id}`
- `POST /api/bookings`
- `GET /api/bookings/{id}`
- `POST /api/bookings/{id}/cancel`
- `GET /actuator/health`

Inventory is in memory and resets on restart. No database is required.

## 1. Overview & Assigned Change Request
* **Set:** Set-F
* **Defect Fixed:** Resolved state inconsistency in equipment booking operations by adding validation for booking transitions, ensuring duplicate state mutations are rejected, and maintaining transactional consistency across concurrent operations.

## 2. Build & Test Instructions
To clean, compile, and execute unit/integration tests locally:

```bash
# Run unit and state consistency tests
mvn clean test

# Package application into a runnable JAR (skipping tests)
mvn package -DskipTests

## 3. Docker Instructions
### Build Image

Build the Docker container image using the required tag:

```bash
docker build -t campus-equipment-booking:set-f .
```

### Run Container

Run the container in detached mode mapping host port 8080 to container port 8080:

```bash
docker run -d --name campus-booking-app -p 8080:8080 campus-equipment-booking:set-f
```

### Verify Container Health

```bash
curl http://localhost:8080/actuator/health
```

---

## 4. Kubernetes (Minikube / Docker Desktop) Deployment

### Deploy Manifests

Load the image (if using standalone Minikube) and apply deployment & service manifests:

```bash
# Minikube image load (if applicable)
minikube image load campus-equipment-booking:set-f

# Deploy to cluster
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
```

### Health Check Access Method

Expose and query the Actuator health endpoint via port-forwarding:

```bash
# Forward port from Service
kubectl port-forward svc/campus-equipment-booking-service 8080:8080 &

# Query health check
curl http://localhost:8080/actuator/health
```

### Scale Deployment

Scale the deployment to 2 replicas and verify pod readiness:

```bash
kubectl scale deployment campus-equipment-booking --replicas=2
kubectl get pods
```
