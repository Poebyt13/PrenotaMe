
import mysql from 'mysql2/promise'

// connessioni del db
let pool;

// funzione per connettersi al database
const connectDatabase = async () => {
    try {
        pool = mysql.createPool({
            host: process.env.DB_HOST || 'localhost',
            user: process.env.DB_USER || 'root',
            password: process.env.DB_PASSWORD || 'password',
            database: process.env.DB_NAME || 'mydatabase',
            port: 3306,
            waitForConnections: true,
            connectionLimit: 10,
            queueLimit: 0
        })

        const connection = await pool.getConnection();
        console.log('Database connesso correttamente!');
        connection.release();

        return pool;

    } catch (error) {
        console.error('Error durante la connessione del DB:', error);
        process.exit(1);
    }
}

// funzione per ottenere il pool aggiornato
const getPool = () => pool;

export { connectDatabase, getPool};