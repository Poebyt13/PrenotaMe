
// Database connection
import { getPool } from "../config/db.js";

// Funzione per ottenere tutti gli eventi
export const getAllEvents = async () => {
    const pool = getPool();
    const [rows] = await pool.execute(`
        SELECT events.*, users.photo as user_photo
        FROM events
        LEFT JOIN users ON events.created_by = users.id
    `);
    return rows;
}


// Funzione per ottenere un evento per ID
export const getEventById = async (eventId) => {
    const pool = getPool();

    const [rows] = await pool.execute(`
        SELECT events.*, users.photo AS user_photo
        FROM events
        LEFT JOIN users ON events.created_by = users.id
        WHERE events.id = ?
    `, [eventId]);

    return rows[0] || null;
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

    await pool.execute(
        `INSERT INTO bookings (user_id, event_id) VALUES (?, ?)`,
        [eventData.created_by, result.insertId]
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

// Funzione per aggiornare un evento
export const updateEvent = async (eventId, eventData) => {
    const pool = getPool();
    const [result] = await pool.execute(
        `UPDATE events
         SET title = ?, description = ?, image_url = ?
         WHERE id = ?`,
        [eventData.title, eventData.description, eventData.image_url, eventId]
    );

    if (result.affectedRows === 0) {
        throw new Error('Evento non trovato o nessuna modifica effettuata');
    }

    return { message: 'Evento aggiornato con successo' };
}

// Funzione per ottenere gli eventi creati da un utente specifico
export const getEventsByCreator = async (creatorId) => {
    const pool = getPool();
    const [rows] = await pool.execute(`
        SELECT events.*, users.photo as user_photo
        FROM events
        LEFT JOIN users ON events.created_by = users.id
        WHERE events.created_by = ?
    `, [creatorId]);
    return rows;
}


// Funzione per ottenere tutti gli eventi a cui l'utente ha prenotato
export const getBookedEventsByUser = async (userId) => {
    const pool = getPool();
    const [rows] = await pool.execute(`
        SELECT events.*, users.photo as user_photo
        FROM events
        LEFT JOIN users ON events.created_by = users.id
        INNER JOIN bookings ON events.id = bookings.event_id
        WHERE bookings.user_id = ?
    `, [userId]);
    return rows;
}