---
status: Exposed
tags: [gcp, iam, review]
sr-due: 2026-06-11
sr-interval: 1
sr-ease: 250
---

# IAM Role vs IAM Policy tren GCP

> Ghi chu ly thuyet — Claude viet sau khi khai thac khai niem trong doi thoai.
> Trang thai: `Exposed` — can Feynman check de nang len `Partial` hoac `Understood`.

---

## 1. IAM Role — "Duoc lam nhung gi?"

[[IAM Role]] la mot tap hop cac **permissions** (quyen truy cap cu the vao cac API/resource).

Role tu no KHONG cap quyen cho bat ky ai. No chi la mot "ban mo ta quyen". Quyen chi co hieu luc khi Role duoc gan (bind) vao mot chu the cu the thong qua mot [[IAM Policy]].

### Ba loai Role trong GCP

| Loai | Mo ta | Vi du |
|---|---|---|
| **Basic Role** (Co ban) | Pham vi rong, ap dung toan bo project. Ton tai truoc IAM. | `roles/owner`, `roles/editor`, `roles/viewer` |
| **Predefined Role** (Dinh san) | Tao boi Google, pham vi theo tung service. Luon duoc cap nhat khi service them API moi. | `roles/storage.admin`, `roles/compute.instanceAdmin` |
| **Custom Role** (Tuy chinh) | Do nguoi dung tu tao, ghep cac permissions le theo nhu cau to chuc. | `projects/my-project/roles/myCustomRole` |

**Nguyen tac least privilege:** Luon chon Role co pham vi nho nhat dap ung duoc yeu cau, tranh dung Basic Role trong moi truong production.

---

## 2. IAM Policy — "Ai duoc giu Role nao, tren Resource nao?"

[[IAM Policy]] (chinh xac la **IAM allow policy**) la mot tai lieu JSON duoc gan vao mot resource. No chua mot danh sach cac **bindings**, moi binding co dang:

```
{
  "bindings": [
    {
      "role": "roles/storage.admin",
      "members": [
        "user:alice@example.com",
        "serviceAccount:my-sa@my-project.iam.gserviceaccount.com",
        "group:devs@example.com"
      ]
    }
  ]
}
```

**Cong thuc ghi nho:**

```
Policy = { (Member + Role) } ap len mot Resource
```

- **Member / Principal**: user, serviceAccount, group, domain, allUsers, allAuthenticatedUsers.
- **Role**: bat ky Role nao (Basic, Predefined, hoac Custom).
- **Resource**: co the la Organization, Folder, Project, hoac resource cu the (bucket, VM...).

---

## 3. So sanh nhanh

| Khia canh | [[IAM Role]] | [[IAM Policy]] |
|---|---|---|
| Chua gi? | Danh sach permissions | Danh sach bindings (Member + Role) |
| Tra loi cau hoi | "Duoc lam gi?" | "Ai duoc lam gi, o dau?" |
| Gap vao Resource? | Khong | Co — Policy gan truc tiep vao resource |
| Tu no co hieu luc? | Khong | Co — hieu luc ngay khi duoc set |

---

## 4. Ke thua theo Resource Hierarchy

[[Resource Hierarchy]] trong GCP co cau truc phan cap:

```
Organization
  Folder
    Project
      Resource (VM, Bucket, ...)
```

**Quy tac ke thua:** Policy o cap cha duoc **ke thua xuong** tat ca cap con. Policy o cap con KHONG the thu hep quyen da cap o cap cha.

Vi du: neu `alice@example.com` duoc gan `roles/viewer` tai Organization, thi Alice tu dong co quyen viewer tren moi Project, moi Folder, moi resource ben trong — du Project do co set Policy rieng hay khong.

**He qua quan trong:** Nguoi co quyen quan tri cap cao hon (Organization Admin) co the leo thang quyen xuong bat ky resource nao. Day la ly do cau hinh [[IAM]] o cap Organization can duoc kiem soat chat.

---

## 5. Vi du minh hoa: gan quyen tren mot Cloud Storage Bucket

Tinh huong: cho phep `bob@example.com` quan tri mot bucket cu the, khong cap quyen ra ngoai bucket do.

**Buoc 1 — Chon Role:** `roles/storage.admin` (Predefined Role) — gom cac permissions nhu `storage.objects.create`, `storage.objects.delete`, `storage.buckets.update`...

**Buoc 2 — Tao binding trong Policy cua bucket:**

```json
{
  "bindings": [
    {
      "role": "roles/storage.admin",
      "members": ["user:bob@example.com"]
    }
  ]
}
```

**Ket qua:** Bob co toan quyen tren bucket nay. Bob KHONG co quyen tren cac bucket khac trong cung Project (vi Policy chi gan o cap bucket, khong phai Project).

**Lenh gcloud tuong duong:**

```bash
gcloud storage buckets add-iam-policy-binding gs://my-bucket \
  --member="user:bob@example.com" \
  --role="roles/storage.admin"
```

---

## 6. Cau hoi tu kiem (Socratic)

1. Neu mot [[Service Account]] duoc gan `roles/compute.instanceAdmin` o cap **Project**, nhung mot VM cu the trong project do lai co Policy rieng gan `roles/compute.viewer` cho cung service account do — service account nay co the start/stop VM do khong? Tai sao?

2. Tai sao `roles/editor` (Basic Role) lai duoc coi la nguy hiem trong moi truong production, du no "tien loi" hon Predefined Role? Hay nghi ve mat principal of least privilege.

3. Neu ban can cap quyen cho mot nhom 50 developer cung truy cap vao mot tap hop bucket, ban nen dung Member loai nao trong Policy binding? Tai sao khong nen liet ke 50 user mot?

---

> **Note to Claude:** Status stays `Exposed` until the user passes a Feynman check (mechanism-level explanation of the Role/Policy distinction and inheritance). Grade with SR algorithm above; update frontmatter after check.
