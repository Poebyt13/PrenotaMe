import express from 'express';

// Funzioni del controller per l'autenticazione
import * as authController from '../controllers/authController.js';

// Creazione del router
const router = express.Router();

// login e registrazione routes
router.post('/register', authController.register);
router.post('/login', authController.login);
router.post('/complete-profile', authController.completeProfile);
router.get('/user/:id', authController.getUserById);

export default router;