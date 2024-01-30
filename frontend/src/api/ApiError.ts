class ApiError extends Error {
  constructor(message?: string) {
    super(message); // Passa el mensaje al constructor de Error
    this.name = "APIError"; // Establece el nombre de la clase de error

    // Esto corrige la pila de llamadas en algunos entornos
    if (typeof Error.captureStackTrace === "function") {
      Error.captureStackTrace(this, this.constructor);
    } else {
      this.stack = new Error(message).stack;
    }
  }
}

export default ApiError;
