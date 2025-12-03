
// database connection
import { getPool } from "../config/db.js";

// Funzione per ottenere tutte le categorie
export const getAllCategories = async () => {
  const pool = getPool();
  const [rows] = await pool.execute("SELECT * FROM categories");
  return rows;
};

// Funzione per ottenere una categoria per ID
export const getCategoryById = async (id) => {
  const pool = getPool();
  const [rows] = await pool.execute("SELECT * FROM categories WHERE id = ?", [
    id,
  ]);
  if (rows.length === 0) {
    throw new Error("ExpectedError: Categoria non trovata");
  }
  return rows[0];
};