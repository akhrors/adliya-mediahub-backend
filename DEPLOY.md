# Adliya MediaHub — Deploy Qo'llanmasi

## Arxitektura

```
Vercel (Frontend)  ──►  Render.com (Backend Spring Boot)  ──►  Render PostgreSQL (Free 90 days)
```

## Foydalanuvchi ma'lumotlari (Test data)

| F.I.Sh. | Email | Parol | Rol |
|---------|-------|-------|-----|
| Toshmatov Aziz | admin@adliya.uz | Admin@12345 | Super Admin |
| Musayev Sherzod | vazir@adliya.uz | Admin@12345 | Rahbariyat |
| Nazarova Mohira | axborot.boshlig@adliya.uz | Admin@12345 | Axborot xizmati admin |
| Rahimov Jasur | jasur.rahimov@adliya.uz | Admin@12345 | Axborot xizmati |
| Holmatov Behruz | nhh.boshliq@adliya.uz | Admin@12345 | Tuzilma rahbari |
| Yusupova Nilufar | fhdy.boshliq@adliya.uz | Admin@12345 | Tuzilma rahbari |
| Mirzayev Akbar | davxizmat.boshliq@adliya.uz | Admin@12345 | Tuzilma rahbari |
| Xoliqova Zulfiya | nhh.pr@adliya.uz | Admin@12345 | PR menejer |
| Ergashev Dilshod | fhdy.pr@adliya.uz | Admin@12345 | PR menejer |
| Tursunov Sanjar | toshkent.shahar@adliya.uz | Admin@12345 | Hududiy admin |
| Karimova Dilorom | samarqand@adliya.uz | Admin@12345 | Hududiy admin |
| Abdullayev Ulmas | fargona@adliya.uz | Admin@12345 | Hududiy admin |

---

## 1-QADAM: GitHub repolarini yaratish

```bash
# Backend repo
cd adliya-mediahub-backend
git init
git add .
git commit -m "feat: initial Adliya MediaHub backend"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/adliya-mediahub-backend.git
git push -u origin main

# Frontend repo
cd ../adliya-mediahub-frontend
git init
git add .
git commit -m "feat: initial Adliya MediaHub frontend"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/adliya-mediahub-frontend.git
git push -u origin main
```

---

## 2-QADAM: Render.com — Backend + PostgreSQL

### 2.1 Hisob yaratish
1. https://render.com ga kiring
2. GitHub bilan ro'yxatdan o'ting

### 2.2 PostgreSQL bazasini yaratish
1. Dashboard → **New +** → **PostgreSQL**
2. To'ldiring:
   - **Name:** `adliya-mediahub-db`
   - **Database:** `adliya_mediahub`
   - **User:** `mediahub`
   - **Region:** Frankfurt (EU Central) — Toshkentga yaqinroq
   - **Plan:** Free
3. **Create Database** tugmasini bosing
4. Baza yaratilgach, **Connection String**ni nusxa oling (keyinchalik kerak bo'ladi)

### 2.3 Backend Web Service yaratish
1. Dashboard → **New +** → **Web Service**
2. **Connect a repository** → `adliya-mediahub-backend` ni tanlang
3. To'ldiring:
   - **Name:** `adliya-mediahub-backend`
   - **Region:** Frankfurt (EU Central)
   - **Branch:** `main`
   - **Runtime:** **Docker**
   - **Plan:** Free
4. **Environment Variables** bo'limini oching va qo'shing:

| Key | Value |
|-----|-------|
| `SPRING_PROFILES_ACTIVE` | `prod` |
| `DB_URL` | *(PostgreSQL dan nusxa olgan Internal DB URL)* |
| `DB_USERNAME` | `mediahub` |
| `DB_PASSWORD` | *(Render bergan parol)* |
| `JWT_SECRET` | `adliya-mediahub-super-secret-key-2026-minimum-32chars` |
| `CORS_ORIGINS` | `https://adliya-mediahub.vercel.app` |
| `PORT` | `8080` |

5. **Create Web Service** → Deploy boshlanadi (5-10 daqiqa)
6. Deploy tugagach URL: `https://adliya-mediahub-backend.onrender.com`
7. Test: `https://adliya-mediahub-backend.onrender.com/api/v1/auth/health`

> ⚠️ **Muhim:** Free tier 15 daqiqa faolsizlikdan keyin "uyquga ketadi". Birinchi so'rovda 30-50 soniya kutish mumkin.

---

## 3-QADAM: Vercel — Frontend

### 3.1 Deploy
1. https://vercel.com ga kiring
2. GitHub bilan ro'yxatdan o'ting
3. **Add New Project** → `adliya-mediahub-frontend` reponi import qiling
4. **Framework Preset:** Next.js (avtomatik aniqlanadi)
5. **Environment Variables** qo'shing:

| Key | Value |
|-----|-------|
| `NEXT_PUBLIC_API_URL` | `https://adliya-mediahub-backend.onrender.com` |

6. **Deploy** tugmasini bosing (2-3 daqiqa)
7. URL: `https://adliya-mediahub.vercel.app`

---

## 4-QADAM: CORS sozlamasi

Backend deploy bo'lgach, Render environment variables da:
```
CORS_ORIGINS = https://adliya-mediahub.vercel.app
```
ni frontend URL bilan yangilang (Vercel tomonidan berilgan URL).

---

## 5-QADAM: Tekshirish

```bash
# 1. Backend health check
curl https://adliya-mediahub-backend.onrender.com/api/v1/auth/health

# 2. Login test
curl -X POST https://adliya-mediahub-backend.onrender.com/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@adliya.uz","password":"Admin@12345"}'

# 3. Frontend
# https://adliya-mediahub.vercel.app ni brauzerda oching
```

---

## Local Docker bilan ishlatish

```bash
# Backend papkasida
docker-compose up --build

# Frontend papkasida (alohida terminal)
npm install
npm run dev
```

Ochiq manzillar:
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html

---

## API Endpointlar

| Module | Endpoint |
|--------|----------|
| Auth | `POST /api/v1/auth/login` |
| Users | `GET/POST /api/v1/users` |
| Organizations | `GET/POST /api/v1/organizations` |
| OAV So'rovlari | `GET/POST /api/v1/media-requests` |
| Tadbirlar | `GET/POST /api/v1/events` |
| Faoliyat yoritish | `GET/POST /api/v1/coverage-materials` |
| Monitoring | `GET/POST /api/v1/monitoring` |
| Reyting | `GET /api/v1/ratings/rules` |
| Dashboard | `GET /api/v1/dashboard/stats` |
| Bildirishnomalar | `GET /api/v1/notifications` |
| Kontent kalendar | `GET/POST /api/v1/content-calendar` |

Swagger UI: `https://adliya-mediahub-backend.onrender.com/swagger-ui.html`

---

## Test ma'lumotlari tarkibi

| Jadvalli | Yozuvlar soni |
|----------|---------------|
| Tashkilotlar | 21 (1 markaziy + 6 boshqarma + 14 hudud/muassasa) |
| Foydalanuvchilar | 13 (barcha rollar bo'yicha) |
| Ekspertlar | 4 (profillari bilan) |
| OAV (media_outlets) | 12 (TV, radio, internet, gazeta) |
| OAV So'rovlari | 5 (turli statuslarda) |
| Tadbirlar | 4 (turli statuslarda) |
| Yoritish materiallari | 6 (TV, Telegram, Instagram, YouTube) |
| Monitoring | 5 (tanqidiy + NHH) |
| Kontent kalendar | 5 |
| Bildirishnomalar | 4 |
| Reyting qoidalari | 12 (TZdan olingan) |
