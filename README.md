# Capstone Epicode — Monorepo

Progetto full-stack composto da:

| Cartella | Tecnologia | Descrizione |
|----------|-----------|-------------|
| `frontend/` | React + TypeScript + Vite | Interfaccia utente |
| `backend/` | Spring Boot 4 + PostgreSQL | REST API |

---

## Prerequisiti

- **Node.js** 18+
- **Java** 21+
- **PostgreSQL** con un database chiamato `capstone_db`
- **Maven** (oppure usa `./mvnw` incluso nel progetto)

---

## Configurazione

### Backend

1. Copia il file di esempio e inserisci i tuoi valori:
   ```bash
   cp backend/src/env.properties.example backend/src/env.properties
   ```
2. Modifica `backend/src/env.properties` con le credenziali del tuo database e i tuoi segreti.

### Frontend

1. Copia il file di esempio:
   ```bash
   cp frontend/.env.example frontend/.env
   ```
2. Modifica `frontend/.env` se il backend gira su una porta diversa da `8080`.

---

## Avvio in sviluppo

### 1 — Backend (porta 8080)

```bash
cd backend
./mvnw spring-boot:run
```

### 2 — Frontend (porta 5173)

```bash
cd frontend
npm install
npm run dev
```

Apri il browser su [http://localhost:5173](http://localhost:5173).

---

## Build di produzione

```bash
# Frontend
cd frontend && npm run build   # output in frontend/dist/

# Backend
cd backend && ./mvnw package   # output in backend/target/
```
