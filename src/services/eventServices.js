
// Database connection
import { getPool } from "../config/db.js";

// Funzione per ottenere tutti gli eventi
export const getAllEvents = async () => {
    const pool = getPool();
    const [rows] = await pool.execute('SELECT * FROM events');
    return rows;
}

// Funzione per ottenere un evento per ID
export const getEventById = async (id) => {
    const pool = getPool();
    const [rows] = await pool.execute('SELECT * FROM events WHERE id = ?', [id]);
    if (rows.length === 0) {
        throw new Error('Evento non trovato');
    }
    return rows[0];
}

// Funzione per creare un nuovo evento
export const createEvent = async (eventData) => {
    const pool = getPool();
    const [result] = await pool.execute(
        `INSERT INTO events
            (title, description, date_start, date_end, category_id, location, seats_total, seats_available, created_by, image_url)
         VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)`,
        [eventData.title, eventData.description, eventData.date_start, eventData.date_end, eventData.category_id, eventData.location, eventData.seats_total, eventData.seats_total, eventData.created_by, eventData.image_url]
    );

    return { id: result.insertId, ...eventData };
}

// Funzione per eliminare un evento
export const deleteEvent = async (id) => {
    const pool = getPool();
    const [result] = await pool.execute('DELETE FROM events WHERE id = ?', [id]);
    if (result.affectedRows === 0) {
        throw new Error('Evento non trovato o già eliminato');
    }
    return { message: 'Evento eliminato con successo' };
}