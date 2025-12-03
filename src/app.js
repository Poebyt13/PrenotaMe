import express from 'express';

// Middleware & Utilities
import { errorHandler } from './middleware/errorMiddleware.js';
import cors from 'cors';
import cookieParser from 'cookie-parser';

// Cors configuration
import corsPolicy from './config/corsPolicy.js';

// Routes
import authRoutes from './routes/authRoutes.js';
import eventRoutes from './routes/eventRoutes.js';
import categoryRoutes from './routes/categoryRoutes.js';
import bookingRoutes from './routes/bookingRoutes.js';

const app = express()

// global middlewares
app.use(cors(corsPolicy))
app.use(express.json())
app.use(express.urlencoded({ extended: true }))
app.use(cookieParser())

// routes
app.use('/api/auth', authRoutes)
app.use('/api/events', eventRoutes)
app.use('/api/categories', categoryRoutes)
app.use('/api/bookings', bookingRoutes);

// error handler middleware
app.use(errorHandler)

export default app;