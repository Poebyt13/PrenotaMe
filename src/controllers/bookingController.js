
import * as bookinService from '../services/bookingService.js';

// prenota un eveno
export const createBooking = async (req, res, next) => {
  try {
    const { eventId, userId } = req.body;
    const booking = await bookinService.bookEvent(eventId, userId);
    res.status(201).json({ success: true, data: booking });
  } catch (error) {
    next(error);
  }
}