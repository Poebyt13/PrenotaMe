
import * as eventService from '../services/eventServices.js';

// Funzione per ottenere tutti gli eventi
export const getEvents = async (req, res, next) => {
    try {
        const events = await eventService.getAllEvents();
        res.status(200).json({ success: true, data: events });
    } catch (error){
        next(error);
    }
}

// Funzione per ottenere un evento per ID
export const getEventById = async (req, res, next) => {
    try {
        const event = await eventService.getEventById(req.params.id);
        res.status(200).json({ success: true, data: event });
    } catch (error) {
        next(error);
    }
}

// Funzione per creare un nuovo evento
export const createEvent = async (req, res, next) => {
    try {
        const userId = req.user.id;
        const eventData = req.body;
        const newEvent = await eventService.createEvent(eventData, userId);
        res.status(201).json({ success: true, data: newEvent });
    } catch (error) {
        next(error);
    }
}

// Funzione per eliminare un evento
export const deleteEvent = async (req, res, next) => {
    try {
        const result = await eventService.deleteEvent(req.params.id);
        res.status(200).json({ success: true, data: result });
    } catch (error) {
        next(error);
    }
}