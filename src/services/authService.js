// Database connection
import { getPool } from "../config/db.js";

// Hashing delle password
import bcrypt from "bcrypt";

// Funzione per la registrazione dell'utente
export const registerUser = async (email, password) => {
  const pool = getPool();
  const [rows] = await pool.execute("SELECT * FROM users WHERE email = ?", [
    email,
  ]);
  if (rows.length > 0) {
    throw new Error("ExpectedError: Email già in uso!");
  }

  const hashedPassword = bcrypt.hashSync(password, 10);

  await pool.execute("INSERT INTO users (email, password) VALUES (?, ?)", [
    email,
    hashedPassword,
  ]);

  return { message: "Utente registrato con successo" };
};

// Funzione per il login dell'utente
export const loginUser = async (email, password) => {
  const pool = getPool();
  const [rows] = await pool.execute("SELECT * FROM users WHERE email = ?", [
    email,
  ]);

  if (rows.length === 0) {
    throw new Error("ExpectedError: Utente non trovato");
  }

  const user = rows[0];
  if (!bcrypt.compareSync(password, user.password)) {
    throw new Error("ExpectedError: Password errata");
  }

  return {
    id: user.id,
    email: user.email,
    username: user.username,
    description: user.description,
    photo: user.photo,
    is_admin: user.is_admin,
    created_at: user.created_at,
  };
};

// Funzioni per completare il profilo utente
export const completeUserProfile = async (id, username, description) => {
  const pool = getPool();
  await pool.execute(
    "UPDATE users SET username = ?, description = ? WHERE id = ?",
    [username, description, id]
  );

  return { message: "Profilo utente aggiornato con successo" };
}