# Mục tiêu DevOps

## Mục tiêu cuối cùng
Có thể **triển khai được hệ thống [[microservice]]** hoàn chỉnh lên môi trường production — từ build image, orchestrate container, đến CI/CD tự động và monitoring.

## Vị trí hiện tại
Chưa bắt đầu. Biết code, chưa có kiến thức DevOps nào.

## Lộ trình (từ cơ bản đến nâng cao)

### Giai đoạn 1 — Nền tảng
- [ ] [[Linux]] cơ bản cho DevOps (filesystem, process, networking, shell scripting)
- [ ] [[Docker]] — container, image, Dockerfile, volumes, networks
- [ ] [[Docker Compose]] — chạy multi-container trên local

### Giai đoạn 2 — Container Orchestration
- [ ] [[Kubernetes]] cơ bản — Pod, Deployment, Service, Namespace
- [ ] [[Kubernetes]] nâng cao — ConfigMap, Secret, Ingress, PersistentVolume
- [ ] Helm — package manager cho Kubernetes

### Giai đoạn 3 — CI/CD
- [ ] [[GitHub Actions]] — build, test, push image tự động
- [ ] Chiến lược deploy: Rolling update, Blue/Green, Canary

### Giai đoạn 4 — Observability
- [ ] [[Prometheus]] + [[Grafana]] — metrics & dashboard
- [ ] Logging tập trung (EFK stack hoặc Loki)
- [ ] Distributed tracing cơ bản

### Giai đoạn 5 — Production Microservices
- [ ] Service mesh ([[Istio]] hoặc Linkerd) — traffic management, mTLS
- [ ] Triển khai hoàn chỉnh hệ thống microservice lên cluster thực tế
- [ ] Xử lý sự cố: debug Pod, đọc log, rollback deploy
