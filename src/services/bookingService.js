
// Database connection
import { getPool } from "../config/db.js";

// Funzione per prenotare un evento
export const bookEvent = async (eventId, userId) => {
    const pool = getPool();

    // transazione per garantire coerenza
    const conn = await pool.getConnection();
    try {
        await conn.beginTransaction();

        // verifico se l'utente ha già prenotato questo evento
        const [existing] = await conn.execute(
            'SELECT id FROM bookings WHERE event_id = ? AND user_id = ?',
            [eventId, userId]
        );
        if (existing.length > 0) {
            throw new Error('ExpectedError: Hai già prenotato questo evento');
        }

        const [eventRows] = await conn.execute(
            'SELECT seats_available FROM events WHERE id = ? FOR UPDATE',
            [eventId]
        );
        if (eventRows.length === 0) {
            throw new Error('ExpectedError: Evento non trovato');
        }
        const event = eventRows[0];
        if (event.seats_available <= 0) {
            throw new Error('ExpectedError: Posti esauriti');
        }

        await conn.execute('UPDATE events SET seats_available = seats_available - 1 WHERE id = ?', [eventId]);
        await conn.execute('INSERT INTO bookings (event_id, user_id) VALUES (?, ?)', [eventId, userId]);

        await conn.commit();
        return { message: 'Prenotazione effettuata con successo' };
    } catch (err) {
        await conn.rollback();
        throw err;
    } finally {
        conn.release();
    }
}