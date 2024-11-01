package modelo;

import excepciones.EquipoRepetidoException;

import java.util.Objects;

/**
 * La clase Conexion representa una conexión entre dos equipos
 * en una red, incluyendo detalles sobre el tipo de cable y
 * los puertos utilizados en cada equipo.
 */
public class Conexion {
    private Equipo equipo1;
    private Equipo equipo2;
    private TipoCable tipoCable;
    private TipoPuerto tipoPuertoEquipo1;
    private TipoPuerto tipoPuertoEquipo2;
    private boolean estado;

    /**
     * Constructor por defecto.
     */
    public Conexion() {
    }

    /**
     * Constructor que inicializa una conexión con los parámetros
     * dados.
     *
     * @param equipo1             El primer equipo de la conexión.
     * @param equipo2             El segundo equipo de la conexión.
     * @param tipoCable           El tipo de cable utilizado en la conexión.
     * @param tipoPuertoEquipo1    El tipo de puerto del primer equipo.
     * @param tipoPuertoEquipo2    El tipo de puerto del segundo equipo.
     * @param estado              El estado de la Conexion.
     */
    public Conexion(Equipo equipo1, Equipo equipo2, TipoCable tipoCable, TipoPuerto tipoPuertoEquipo1, TipoPuerto tipoPuertoEquipo2, boolean estado) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.tipoCable = tipoCable;
        this.tipoPuertoEquipo1 = tipoPuertoEquipo1;
        this.tipoPuertoEquipo2 = tipoPuertoEquipo2;
        this.estado = estado;
    }

    public Equipo getEquipo1() {
        return equipo1;
    }

    public void setEquipo1(Equipo equipo1) throws EquipoRepetidoException {
        if(this.equipo2 == equipo1)
            throw new EquipoRepetidoException("Los equipos 1 y 2 son el mismo.");

        this.equipo1 = equipo1;
    }

    public Equipo getEquipo2() { return equipo2; }

    public void setEquipo2(Equipo equipo2) throws EquipoRepetidoException{
        if(this.equipo1 == equipo2)
            throw new EquipoRepetidoException("Los equipos 1 y 2 son el mismo.");

        this.equipo2 = equipo2;
    }

    public TipoPuerto getTipoPuertoEquipo1() {
        return tipoPuertoEquipo1;
    }

    public void setTipoPuertoEquipo1(TipoPuerto tipoPuertoEquipo1) {
        this.tipoPuertoEquipo1 = tipoPuertoEquipo1;
    }

    public TipoPuerto getTipoPuertoEquipo2() {
        return tipoPuertoEquipo2;
    }

    public void setTipoPuertoEquipo2(TipoPuerto tipoPuertoEquipo2) {
        this.tipoPuertoEquipo2 = tipoPuertoEquipo2;
    }

    public TipoCable getTipoCable() {
        return tipoCable;
    }

    public void setTipoCable(TipoCable tipoCable) {
        this.tipoCable = tipoCable;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Conexion{" +
                "equipo1=" + equipo1 +
                ", equipo2=" + equipo2 +
                ", tipoCable=" + tipoCable +
                ", tipoPuertoEquipo1=" + tipoPuertoEquipo1 +
                ", tipoPuertoEquipo2=" + tipoPuertoEquipo2 +
                ", estado=" + estado +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conexion conexion = (Conexion) o;
        return isEstado() == conexion.isEstado() && getEquipo1().getCodigo() == conexion.getEquipo1().getCodigo() && getEquipo2().getCodigo() == conexion.getEquipo2().getCodigo() && getTipoCable().equals(conexion.getTipoCable()) && getTipoPuertoEquipo1().equals(conexion.getTipoPuertoEquipo1()) && getTipoPuertoEquipo2().equals(conexion.getTipoPuertoEquipo2());
    }

}
