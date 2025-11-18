
export const errorHandler = (err, req, res, next) => {
    console.error(err.stack);
    res.status(err.status || 500).json({
        success: false,
        messagge: err.message || 'Errore del server',
    })
}