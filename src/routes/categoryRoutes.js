import express from 'express';

// Funzioni per gestire gli eventi
import * as categoryController from '../controllers/categoryController';

// Creazione del router
const router = express.Router();

// Eventi routes
router.get('/', categoryController.getCategories);

export default router;