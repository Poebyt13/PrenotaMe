
# PrenotaMe — Istruzioni rapide


#### Docente
- Antonio Giovanni Lezzi
#### Partecipanti
- Moaaz Bahnassawi
- Jose Luis Calderon Ludena



---

#### Che cosa fa
- App Android (Java) per gestire e prenotare eventi.
- Backend REST in Node.js/Express.
- Database relazionale MySQL per utenti, eventi e prenotazioni.

#### Tecnologie principali
- Android (Java) + Android Studio
- Node.js, Express
- MySQL (`mysql2`)
- `bcrypt`, `cookie-parser`, `dotenv`, `cors`

Prima di iniziare la configurazione assicurati di essere nella cartella in cui vuoi mettere il progetto. Qui useremo `progetto-todo` come esempio di cartella di destinazione; se il clone crea una cartella con nome diverso (es. `PrenotaMe`) entra in quella cartella.

### Configurazione `git worktree`
Segui questi comandi sul computer che deve riprodurre la struttura di lavoro:

1) Clona il repository nella cartella scelta:

```
git clone <URL-della-repo> progetto-todo
```

2) Entra nella cartella clonata:

```
cd progetto-todo
```

3) Crea i worktree per `frontend` e `backend` (i comandi creano anche i branch locali basati sui branch remoti):

```
git worktree add -b frontend ./frontend origin/frontend
git worktree add -b backend  ./backend  origin/backend
```

4) Verifica e inizia a lavorare:

```
git worktree list
cd frontend
git status
```


Uso `git worktree` per tenere ogni branch collegato a una cartella separata: il risultato è un progetto ordinato, la possibilità di lavorare contemporaneamente su `main`, `frontend` e `backend` e il vantaggio di non duplicare la storia (rispetto a tre cloni separati).

### Informazioni utili: 
- Backend: entra nella cartella `backend`, crea un file `.env` (vedi `backend/db/README.md` per le variabili), poi esegui `npm install` e `npm start`.
- Frontend: entra nella cartella `frontend` e segui le istruzioni specifiche del progetto frontend (`npm install` e `npm run dev` o simile).
- Database: lo schema SQL è in `backend/db/init.sql`; per istruzioni dettagliate leggi `backend/db/README.md`.
