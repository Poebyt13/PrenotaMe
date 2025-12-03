import express from 'express';

// Funzioni per gestire gli eventi
import * as eventController from '../controllers/eventController.js';

// Creazione del router
const router = express.Router();

// Eventi routes
router.get('/', eventController.getEvents);
router.get('/:id', eventController.getEventById);
router.post('/', eventController.createEvent);
router.delete('/:id', eventController.deleteEvent);
router.put('/:id', eventController.updateEvent);
router.get('/creator/:id', eventController.getEventsByCreator);
router.get('/booked/:id', eventController.getBookedEventsByUser);

export default router;