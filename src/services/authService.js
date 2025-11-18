
// Database connection
import { getPool } from "../config/db.js";

// Hashing delle password   
import bcrypt from 'bcrypt';

// Funzione per il login dell'utente
export const loginUser = async (username, password) => {
    const pool = getPool();
    const [rows] = await pool.execute('SELECT * FROM users WHERE username = ?', [username]);

    if (rows.length === 0) {
        throw new Error('Utente non trovato');
    }

    const user = rows[0];
  
    if (!bcrypt.compareSync(password, user.password)) {
        throw new Error('Password errata');
    }  

    return { id: user.id, username: user.username };
}

// Funzione per la registrazione dell'utente
export const registerUser = async (username, password) => {
    const pool = getPool();
    
    const [rows] = await pool.execute('SELECT * FROM users WHERE username = ?', [username]);
    if (rows.length > 0) {
        throw new Error('Username già esistente');
    }

    const hashedPassword = bcrypt.hashSync(password, 10);

    await pool.execute('INSERT INTO users (username, password) VALUES (?, ?)', [username, hashedPassword]);

    return { message: 'Utente registrato con successo' };
}
