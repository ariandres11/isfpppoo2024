package modelo;

/**
 * La clase TipoPuerto representa un tipo de puerto en la red,
 * incluyendo su código, descripción y velocidad.
 */
public class TipoPuerto {
    private String codigo;
    private String descripcion;
    private int velocidad;

    /**
     * Constructor por defecto.
     */
    public TipoPuerto() {
    }

    /**
     * Obtiene el código del tipo de puerto.
     *
     * @return El código del tipo de puerto.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el código del tipo de puerto.
     *
     * @param codigo El nuevo código del tipo de puerto.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene la descripción del tipo de puerto.
     *
     * @return La descripción del tipo de puerto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del tipo de puerto.
     *
     * @param descripcion La nueva descripción del tipo de puerto.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la velocidad del tipo de puerto.
     *
     * @return La velocidad del tipo de puerto en Mbps.
     */
    public int getVelocidad() {
        return velocidad;
    }

    /**
     * Establece la velocidad del tipo de puerto.
     *
     * @param velocidad La nueva velocidad del tipo de puerto en Mbps.
     */
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    @Override
    public String toString() {
        return "TipoPuerto{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", velocidad=" + velocidad +
                '}';
    }
}
