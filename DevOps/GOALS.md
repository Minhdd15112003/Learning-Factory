# Mục tiêu — DevOps Brain

## Mục tiêu tối thượng
Lấy chứng chỉ **CKA (Certified Kubernetes Administrator)** — chứng chỉ vận hành Kubernetes cấp độ administrator, do CNCF cấp.

## Vị trí hiện tại
Bắt đầu từ zero. Chưa có kiến thức nền tảng về Kubernetes, container, hay DevOps nói chung.

## Lộ trình sơ bộ

### Giai đoạn 1 — Nền tảng Container & Linux
- Hiểu container là gì, tại sao dùng Docker
- Nắm được Linux cơ bản (process, namespace, cgroup) vì CKA thi trực tiếp trên terminal
- Công cụ: `docker`, `kubectl`

### Giai đoạn 2 — Kubernetes Core Concepts
- [[Pod]], [[Node]], [[Cluster]], [[Namespace]]
- [[Deployment]], [[ReplicaSet]], [[Service]]
- [[ConfigMap]], [[Secret]], [[Volume]]
- [[Scheduler]], [[etcd]], [[API Server]], [[kubelet]]

### Giai đoạn 3 — CKA Domain chính
- Cluster Architecture, Installation & Configuration (25%)
- Workloads & Scheduling (15%)
- Services & Networking (20%)
- Storage (10%)
- Troubleshooting (30%)

### Giai đoạn 4 — Luyện đề & Thi thử
- Killer.sh (môi trường thi thử chính thức)
- Practice lab tự dựng (kind / kubeadm)

## Ghi chú
- CKA là thi thực hành hoàn toàn (không trắc nghiệm) — cần luyện tay trên terminal.
- Thời gian thi: 2 tiếng, 17 câu, ngưỡng đạt 66%.
