---
status: Partial
tags: [terraform, state, backend, review]
sr-due: 2026-06-11
sr-interval: 1
sr-ease: 250
---

# Terraform State

> Tham chieu: [[01-Ban-chat-cua-Terraform]] | [[COMMANDS]]

---

## 1. Terraform State la gi?

File `terraform.tfstate` la "cuon so cai" (ledger) cua [[Terraform]]. No luu tru anh xa (mapping) giua nhung gi ban mo ta trong code `.tf` va nhung tai nguyen dang thuc su ton tai tren [[GCP]].

**Tai sao Terraform can no?**

[[Terraform]] khong dua vao ten resource de nhan biet. Khi ban chay `terraform apply`, Terraform khong hoi GCP "bucket ten nay co ton tai khong?" — no doc file state de biet minh da tung tao gi, voi ID gi, o dau. Neu khong co state, Terraform la mot cong cu mu: no khong biet hien trang the gioi la gi, nen no se co gang tao moi tat ca.

**Cau truc cua file state (dan gian hoa):**

File `terraform.tfstate` la mot file JSON. No chua:
- Ten logical cua resource trong code (vi du: `google_storage_bucket.my_bucket`)
- ID thuc te cua tai nguyen do tren GCP (vi du: ten bucket tren GCS)
- Cac thuoc tinh hien tai cua tai nguyen do

```json
{
  "resources": [
    {
      "type": "google_storage_bucket",
      "name": "my_bucket",
      "instances": [
        {
          "attributes": {
            "id": "ten-bucket-thuc-te-tren-gcs",
            "location": "US"
          }
        }
      ]
    }
  ]
}
```

---

## 2. Thi nghiem "Mat tri nho": Dieu gi xay ra khi mat State?

**Kich ban:** Ban doi ten hoac xoa file `terraform.tfstate` roi chay `terraform apply` lai.

**Ket qua:** Loi `Conflict 409` tu phia GCP.

**Tai sao?**

Terraform doc State, thay trong rong (khong co resource nao), nen no ket luan: "Chua co gi het, toi can tao moi tat ca." No gui lenh tao bucket den GCP. GCP nhan ra bucket voi ten do da ton tai (do lan truoc tao roi) va tu choi voi loi `409 Conflict` — "Ten nay da duoc dang ky, khong the tao trung."

Day la misconception pho bien: Terraform khong dua vao GCP de kiem tra hien trang. No tin tuong State hon. Neu State noi "chua co", Terraform tin ngay.

**Cach cuu:** Lenh `[[terraform import]]`

```bash
terraform import google_storage_bucket.my_bucket ten-bucket-thuc-te-tren-gcs
```

Lenh nay noi voi Terraform: "Tai nguyen `google_storage_bucket.my_bucket` trong code cua tao da ton tai tren GCP voi ID la `ten-bucket-thuc-te-tren-gcs`. Hay doc thong tin cua no ve va ghi vao State."

Sau khi import, State da co thong tin, va `terraform plan` se thay trang thai mong muon khop voi thuc te — khong con tao trung nua.

---

## 3. Local State va nhung van de cua no

Mac dinh, Terraform luu State o local: file `terraform.tfstate` nam ngay trong thu muc chua code `.tf` cua ban.

**Nhuoc diem cua Local State:**

| Van de | Giai thich |
|---|---|
| Kho lam nhom | Noi co 2 nguoi cung apply, ai giu file state? Nguoi con lai lam viec tren state cu. |
| De mat | May hu, xoa nham, hay quen commit — State bien mat, [[Terraform]] mat tri nho. |
| Chua du lieu nhay cam | File JSON co the chua password, connection string, private key cua cac tai nguyen. Neu commit len Git, du lieu nay bi lo. |

---

## 4. Remote Backend voi [[GCS]]

Giai phap la chuyen State ra khoi may ca nhan va luu tren mot Remote Backend. Voi [[GCP]], backend pho bien nhat la [[GCS]] (Google Cloud Storage).

**Cach cau hinh backend GCS (HCL):**

Trong file `main.tf` hoac mot file rieng `backend.tf`, them khoi `terraform { backend "gcs" { ... } }`:

```hcl
terraform {
  # Khai bao Provider phien ban
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "~> 5.0"
    }
  }

  # Cau hinh Backend: Luu State tren GCS thay vi local
  backend "gcs" {
    # Ten GCS bucket se chua file State
    # Bucket nay phai duoc tao tay TRUOC khi chay terraform init
    bucket = "ten-bucket-chua-state-cua-ban"

    # Duong dan thu muc ben trong bucket
    # Giup to chuc neu nhieu project dung chung 1 bucket
    prefix = "terraform/state"
  }
}
```

**Luu y quan trong:** Bucket dung de chua State (`ten-bucket-chua-state-cua-ban`) KHONG duoc quan ly boi Terraform (khong duoc khai bao nhu 1 resource trong code). No phai duoc tao bang tay (hoac bang mot script rieng) truoc khi chay `terraform init`. Neu de Terraform tu tao bucket chua chinh State cua no, se co van de "con ga - qua trung": Terraform can State de biet no da tao bucket chua-State chua, nhung no chua co State...

Sau khi cau hinh, chay `terraform init` de Terraform ket noi va migrate State len GCS.

---

## 5. State Locking: Chong Race Condition

**Van de:** Trong team, A va B cung chay `terraform apply` cung luc. Ca hai deu doc State, tinh toan plan, roi cung ghi State voi nhung thay doi cua rieng minh. Ket qua: State bi corrupt, tai nguyen tren GCP khong khop voi State.

**Giai phap — State Locking:**

Khi su dung Remote Backend (GCS), [[Terraform]] tu dong kich hoat khoa State (lock) ngay khi bat dau mot lenh thay doi (`plan`, `apply`, `destroy`). Co che nay duoc GCS ho tro thong qua `object locking` hoac dich vu Cloud [[Firestore]]/[[Cloud Spanner]] (tuy phien ban va cau hinh).

Qua trinh:
1. A chay `terraform apply`. Terraform ghi mot file lock vao GCS (`terraform.tfstate.tflock`).
2. B cung chay `terraform apply`. Terraform kiem tra GCS, thay file lock, bao loi: "State is locked. Another operation is in progress."
3. A hoan thanh. Terraform xoa file lock.
4. B thu lai va thanh cong.

Ket qua: Chi co 1 nguoi duoc ghi State tai mot thoi diem. Race Condition khong xay ra.

---

## 6. Tai sao GCS tot hon Git de chua State?

| Tieu chi | Git (sai lam) | GCS Remote Backend (dung) |
|---|---|---|
| Bao mat | File `.tfstate` JSON bi push len repo, lo API key, password | State nam tren GCS, truy cap kiem soat boi [[IAM]] |
| Locking | Khong co — 2 nguoi merge conflict tren State | Co co che lock tich hop san |
| Dong bo | Thu cong — phai commit, push, pull | Tu dong — moi lenh terraform deu doc/ghi len GCS truc tiep |
| Lich su phien ban | Dung Git commits (khong phu hop) | GCS ho tro Object Versioning (giu lai cac phien ban State cu) |

---

## Cau hoi tu kiem (Spaced Repetition)

1. Neu ban co mot GCS bucket da ton tai tren GCP nhung file `terraform.tfstate` khong co thong tin gi ve no, dieu gi se xay ra khi ban chay `terraform apply`? Va ban se sua nhu the nao?

2. Khoi `backend "gcs"` duoc khai bao ben trong khoi `terraform {}`. Theo ban, khi nao Terraform doc cau hinh nay — luc chay `terraform plan` hay luc chay `terraform init`? Tai sao?

3. State Locking giai quyet Race Condition bang cach nao cu the? File lock duoc ghi o dau, va no bi xoa khi nao?

---

> **Trang thai hien tai:** `Partial` — Can pass Feynman check o muc co-che (mechanism-level) ve cau hinh backend thuc te: tai sao phai tao bucket chua State bang tay, `terraform init` lam gi voi khoi `backend "gcs"`, va GCS lock hoat dong nhu the nao. Khi giai thich duoc 3 dieu nay ma khong can xem lai note, trang thai se duoc nang len `Understood`.
