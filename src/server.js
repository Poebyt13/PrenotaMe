import app from "./app.js";

// dotenv configuration
import "dotenv/config";

// Database connection
import { connectDatabase } from "./config/db.js";

const PORT = process.env.PORT || 3000;

// Avvio del server dopo la connessione al database
const startServer = async () => {
    try {
        await connectDatabase();

        app.listen(PORT, () => {
            console.log(`Server in ascolto sulla porta ${PORT}`);
        });

    } catch (error) {
        console.error('Errore durante l\'avvio del server:', error);
        process.exit(1);
    }
}

startServer();
