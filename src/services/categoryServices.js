
// database connection
import { getPool } from "../config/db.js";

// Funzione per ottenere tutte le categorie
export const getAllCategories = async () => {
  const pool = getPool();
  const [rows] = await pool.execute("SELECT * FROM categories");
  return rows;
};