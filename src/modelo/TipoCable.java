package modelo;

/**
 * La clase TipoCable representa un tipo de cable en la red,
 * incluyendo su código, descripción y velocidad de transmisión.
 */
public class TipoCable {
    private String codigo;
    private String descripcion;
    private int velocidad;

    /**
     * Constructor por defecto.
     */
    public TipoCable() {
    }

    /**
     * Obtiene el código del tipo de cable.
     *
     * @return El código del tipo de cable.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el código del tipo de cable.
     *
     * @param codigo El nuevo código del tipo de cable.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene la descripción del tipo de cable.
     *
     * @return La descripción del tipo de cable.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del tipo de cable.
     *
     * @param descripcion La nueva descripción del tipo de cable.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la velocidad del tipo de cable.
     *
     * @return La velocidad del tipo de cable.
     */
    public int getVelocidad() {
        return velocidad;
    }

    /**
     * Establece la velocidad del tipo de cable.
     *
     * @param velocidad La nueva velocidad del tipo de cable.
     */
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    @Override
    public String toString() {
        return "TipoCable{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", velocidad=" + velocidad +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoCable tipoCable = (TipoCable) o;
        return codigo.equals(tipoCable.getCodigo());
    }
}
