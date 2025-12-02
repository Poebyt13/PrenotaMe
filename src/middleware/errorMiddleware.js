
export const errorHandler = (err, req, res, next) => {
    console.error(err.stack);

    if (err.message.startsWith('ExpectedError:')) {
        const msg = err.message.replace("ExpectedError: ", "");
        return res.status(200).json({
            success: false,
            messagge: msg
        });
    }

    res.status(err.status || 500).json({
        success: false,
        messagge: err.message || 'Errore del server',
    })
}