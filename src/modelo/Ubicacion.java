package modelo;

import java.util.Objects;

/**
 * La clase Ubicacion representa una ubicación dentro de la red,
 * incluyendo un código único y una descripción.
 */
public class Ubicacion {
    private String codigo;
    private String descripcion;

    /**
     * Constructor por defecto.
     */
    public Ubicacion() {
    }

    /**
     * Constructor que inicializa la ubicación con un código y una descripción.
     *
     * @param codigo     El código de la ubicación.
     * @param descripcion La descripción de la ubicación.
     */
    public Ubicacion(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el código de la ubicación.
     *
     * @return El código de la ubicación.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el código de la ubicación.
     *
     * @param codigo El nuevo código de la ubicación.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene la descripción de la ubicación.
     *
     * @return La descripción de la ubicación.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la ubicación.
     *
     * @param descripcion La nueva descripción de la ubicación.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Ubicacion{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ubicacion ubicacion = (Ubicacion) o;
        return Objects.equals(codigo, ubicacion.codigo);
    }

}
