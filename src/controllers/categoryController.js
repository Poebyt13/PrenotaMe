
import * as categoryService from '../services/categoryServices.js';

// Controller per ottenere tutte le categorie
export const getCategories = async (req, res, next) => {
  try {
    const categories = await categoryService.getAllCategories();
    res.status(200).json({ success: true, data: categories });
  } catch (error) {
    next(error);
  }
}
