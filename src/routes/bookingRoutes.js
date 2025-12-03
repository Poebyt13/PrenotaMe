import express from 'express';

// Funzioni per gestire le prenotazioni
import * as bookinController from '../controllers/bookingController.js';

// Creazione del router
const router = express.Router();

// Booking routes
router.post('/', bookinController.createBooking);


export default router;