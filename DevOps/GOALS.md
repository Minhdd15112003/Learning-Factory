# Mục tiêu — DevOps Brain

## Mục tiêu tối thượng

**Vận hành được production cluster trên Kubernetes** — không phải chỉ deploy được, mà hiểu và xử lý được các tình huống thực tế: scaling, networking, failure recovery, observability, và security trong một môi trường production thật sự.

---

## Vị trí hiện tại

- Xuất phát điểm: background dev, chưa có kinh nghiệm ops.
- Buổi học đầu tiên: chưa bắt đầu.
- Điểm mù chính: tư duy "chạy được là xong" cần chuyển thành "chạy được + quan sát được + recover được".

---

## Lộ trình thô (sẽ tinh chỉnh sau các buổi học đầu)

### Giai đoạn 1 — Nền tảng container & orchestration
- [ ] Hiểu Docker: image, container, layer, registry
- [ ] Hiểu vì sao cần orchestration — limitations của bare Docker
- [ ] Kiến trúc Kubernetes: control plane vs. worker nodes, etcd, API server, scheduler, kubelet
- [ ] Các object cơ bản: Pod, Deployment, ReplicaSet, Service, ConfigMap, Secret

### Giai đoạn 2 — Vận hành cluster
- [ ] Networking trong K8s: CNI, ClusterIP, NodePort, LoadBalancer, Ingress
- [ ] Storage: PV, PVC, StorageClass
- [ ] Scheduling: resource requests/limits, taints, tolerations, affinity
- [ ] Health checks: liveness, readiness, startup probes

### Giai đoạn 3 — Production mindset
- [ ] Observability: logging, metrics (Prometheus), alerting (Alertmanager), tracing
- [ ] RBAC và security context
- [ ] Rolling updates, rollbacks, và zero-downtime deployment
- [ ] Cluster maintenance: node drain, cordon, upgrades

### Giai đoạn 4 — Mục tiêu cuối (stretch)
- [ ] Tự vận hành một production-like cluster (có thể trên cloud hoặc bare metal)
- [ ] Xử lý được incident: node down, pod crash loop, OOMKilled, network policy conflict

---

## Ghi chú

- Sub-project sẽ được tạo khi cần thực hành chuyên sâu một topic cụ thể (dùng `/new-project`).
- Roadmap trên là ước lượng ban đầu — sẽ điều chỉnh sau mỗi weekly review.
