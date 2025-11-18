import express from 'express';

// Funzioni del controller per l'autenticazione
import * as authController from '../controllers/authController.js';

// Creazione del router
const router = express.Router();

// login e registrazione routes
router.post('/register', authController.register);
router.post('/login', authController.login);

export default router;