

# Backend — PrenotaMe 

### Panoramica
- Questo repository contiene il backend Express per l'app PrenotaMe. Gestisce autenticazione, eventi e prenotazioni. Il codice è in fase di sviluppo: le funzionalità principali sono strutturate ma potrebbero non essere complete.

### Installazione e avvio
- Per installare le dipendenze: `npm install`
 - Per avviare il server: `npm start` (esegue `node ./src/server.js`)


## Importante — Database**

Per le variabili d'ambiente e le istruzioni per creare/importare il database, apri `db/README.md` (troverai lo script `init.sql` e tutti i passaggi necessari).

#### Dipendenze principali
- `express`: framework HTTP (v5.x).
- `cors`: gestione CORS.
- `dotenv`: caricamento variabili d'ambiente.
- `mysql2`: driver MySQL (con supporto promise e pool).
- `bcrypt`: hashing password.
- `cookie-parser`: parsing cookie.

#### Configurazione CORS
- File: `src/config/corsPolicy.js`
- Origin consentita di default: `http://localhost:5173`.
- Metodi: `GET, HEAD, PUT, PATCH, POST, DELETE`.
- Header consentiti: `Content-Type, Authorization`.
- `credentials` è abilitato. Se distribuisci in produzione, sostituisci l'origine hard-coded con una variabile d'ambiente (`CORS_ORIGIN`).

#### Configurazione Database
- File: `src/config/db.js` (usa `mysql2/promise` e un pool di connessioni).
- Le impostazioni principali del pool sono: `port: 3306`, `waitForConnections: true`, `connectionLimit: 10`, `queueLimit: 0`.
- Lo script `backend/db/init.sql` contiene la DDL per creare il DB (`AndroidProject`) e le tabelle; esegui quel file nel tuo ambiente MySQL prima di avviare l'app.

---
### Struttura del progetto
- `src/server.js`, `src/app.js`: avvio e setup di Express.
- `src/config/`: configurazioni (CORS, DB).
- `src/routes/`: rotte (`authRoutes.js`, `eventRoutes.js`, `bookingRoutes.js`, `eventRoutes.js`).
- `src/controllers/`: logica dei controller.
- `src/services/`: logica di servizio / accesso al DB.
- `src/middleware/errorMiddleware.js`: gestione centralizzata degli errori.
