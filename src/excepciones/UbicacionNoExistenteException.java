package excepciones;

public class UbicacionNoExistenteException extends RuntimeException{
    public UbicacionNoExistenteException (String mensaje) {
        super(mensaje);
    }
}
