import express from 'express';

// Funzioni per gestire gli eventi
import * as categoryController from '../controllers/categoryController.js';

// Creazione del router
const router = express.Router();

// Eventi routes
router.get('/', categoryController.getCategories);
router.get('/:id', categoryController.getCategoryById);

export default router;