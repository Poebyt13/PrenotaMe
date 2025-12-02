
import * as authService from '../services/authService.js';

// Controller per la registrazione di un nuovo utente
export const register = async (req, res, next) => {
    try {
        const { email, password } = req.body;
        const result = await authService.registerUser(email, password);
        res.status(201).json({ success: true, data: result });
    } catch (error) {
        next(error);
    }
}

// Controller per il login di un utente esistente
export const login = async (req, res, next) => {
    try {
        const { email, password } = req.body;
        const result = await authService.loginUser(email, password);
        res.status(200).json({ success: true, data: result });
    } catch (error) {
        next(error);
    }
}