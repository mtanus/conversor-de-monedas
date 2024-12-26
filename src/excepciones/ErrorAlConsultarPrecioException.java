package excepciones;

public class ErrorAlConsultarPrecioException extends RuntimeException {
    private String mensajeDeError;

    public ErrorAlConsultarPrecioException(String mensaje) {
        this.mensajeDeError = mensaje;
    }

    @Override
    public String getMessage() {
        return this.mensajeDeError;
    }
}
