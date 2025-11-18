## Database — istruzioni rapide

Questo file spiega come usare lo script SQL `init.sql` per creare il database e le tabelle del progetto sul tuo ambiente MySQL

Posizione dello script:
- `backend/db/init.sql`

Come procedere (DataGrip - mySQL Workbench)
- Apri DataGrip e configura la connessione a MySQL (host `localhost`, porta `3306`, user/password).
- Apri la console SQL per quella connessione (tasto destro → "Open Console" o simile).
- Apri o incolla il contenuto di `backend/db/init.sql` nella console e premi *Run*. Lo script creerà il database `AndroidProject` e tutte le tabelle.

File `.env` consigliato (copia in `backend/.env` e modifica le credenziali):

```
DB_HOST=localhost
DB_USER=il_tuo_user_locale
DB_PASSWORD=la_tua_password
DB_NAME=AndroidProject
CORS_ORIGIN=http://localhost:5173
```

Avvio dell'app (dopo aver importato lo schema):
- Nella cartella `backend` esegui `npm install` e poi `npm start`.


##### NOTA FINALE
>Il file `init.sql` è pensato per essere eseguito direttamente nell'ambiente di database: metti il file dove preferisci e lancialo nella console del tuo DB (DataGrip, Adminer, phpMyAdmin, o CLI).
