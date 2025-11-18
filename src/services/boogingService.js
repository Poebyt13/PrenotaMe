
// Database connection
import { getPool } from "../config/db";

// Funzione per prenotare un evento
export const bookEvent = async (eventId, userId) => {
    const pool = getPool();
    
    const [eventRows] = await pool.execute('SELECT seats_available FROM events WHERE id = ?', [eventId]);
    if (eventRows.length === 0) {
        throw new Error('Evento non trovato');
    }
    const event = eventRows[0];
    if (event.seats_available <= 0) {
        throw new Error('Posti esauriti');
    }

    await pool.execute('UPDATE events SET seats_available = seats_available - 1 WHERE id = ?', [eventId]);

    await pool.execute('INSERT INTO bookings (event_id, user_id) VALUES (?, ?)', [eventId, userId]);

    return { message: 'Prenotazione effettuata con successo' };
}