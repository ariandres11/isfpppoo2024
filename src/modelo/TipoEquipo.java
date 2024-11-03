package modelo;

import java.util.Objects;

/**
 * La clase TipoEquipo representa un tipo de equipo en la red,
 * incluyendo su código y descripción.
 */
public class TipoEquipo {
    private String codigo;
    private String descripcion;

    /**
     * Constructor por defecto.
     */
    public TipoEquipo() {
    }

    /**
     * Obtiene el código del tipo de equipo.
     *
     * @return El código del tipo de equipo.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el código del tipo de equipo.
     *
     * @param codigo El nuevo código del tipo de equipo.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene la descripción del tipo de equipo.
     *
     * @return La descripción del tipo de equipo.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del tipo de equipo.
     *
     * @param descripcion La nueva descripción del tipo de equipo.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "TipoEquipo{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoEquipo that = (TipoEquipo) o;
        return Objects.equals(codigo, that.codigo);
    }
}
