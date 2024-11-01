package excepciones;

public class ConexionNoExistenteException extends RuntimeException{
    public ConexionNoExistenteException (String mensaje) {
        super(mensaje);
    }
}
