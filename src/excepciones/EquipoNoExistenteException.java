package excepciones;

public class EquipoNoExistenteException extends RuntimeException{
    public EquipoNoExistenteException (String mensaje) {
        super(mensaje);
    }
}
