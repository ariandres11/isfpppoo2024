package excepciones;

public class ConexionRepetidaException extends RuntimeException{
    public ConexionRepetidaException (String mensaje) {
        super(mensaje);
    }
}
