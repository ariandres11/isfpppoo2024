package modelo;

import excepciones.DireccionIPRepetidaException;
import excepciones.PuertoRepetidoException;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;



/**
 * La clase Equipo representa un dispositivo en una red,
 * incluyendo información sobre su marca, modelo, direcciones IP,
 * puertos disponibles y ubicación.
 */
public class Equipo {
    private String codigo;
    private String descripcion;
    private String marca;
    private String modelo;
    private List<String> direccionesIP;
    private List<Puerto> puertos;
    private Ubicacion ubicacion;
    private TipoEquipo tipoEquipo;
    private boolean estado;
    private Map <String, Integer> mapaPuertos;

    /**
     * Constructor que inicializa un equipo con los parámetros dados.
     *
     * @param codigo           El código del equipo.
     * @param descripcion      Una descripción del equipo.
     * @param marca            La marca del equipo.
     * @param modelo           El modelo del equipo.
     * @param cantidadPuerto   La cantidad de puertos del equipo.
     * @param tipoPuerto       El tipo de puerto del equipo.
     * @param ubicacion        La ubicación del equipo en la red.
     * @param tipoEquipo       El tipo de equipo (por ejemplo, router, switch).
     * @param estado           El estado del equipo.
     */
    public Equipo(String codigo, String descripcion, String marca, String modelo, int cantidadPuerto, TipoPuerto tipoPuerto, Ubicacion ubicacion, TipoEquipo tipoEquipo, boolean estado) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.marca = marca;
        this.modelo = modelo;
        this.mapaPuertos = new HashMap<>();
        this.direccionesIP = new ArrayList<>();
        this.puertos = new ArrayList<>();
        agregarPuerto(cantidadPuerto, tipoPuerto);
        this.ubicacion = ubicacion;
        this.tipoEquipo = tipoEquipo;
        this.estado = estado;
    }

    /**
     * Constructor por defecto.
     */
    public Equipo() {
        this.direccionesIP = new ArrayList<>();
        this.puertos = new ArrayList<>();
        this.mapaPuertos = new HashMap<>();
    }

    /**
     * Obtiene la cantidad de puertos inicial del equipo.
     *
     * @return La cantidad de puertos.
     */
    public int getCantidadPuertoInicial() {
        return puertos.get(0).getCantidad();
    }

    /**
     * Obtiene el tipo de puerto inicial del equipo.
     *
     * @return El tipo de puerto.
     */
    public TipoPuerto getTipoPuertoInicial() {
        return puertos.get(0).getTipoPuerto();
    }

    public Map<String, Integer> getMapaPuertos() {
        return mapaPuertos;
    }
    /**
     * Agrega una dirección IP al equipo si no está ya registrada.
     *
     * @param direccionIP La dirección IP a agregar.
     * @return La dirección IP agregada, o null si ya existe.
     */
    public String agregarDireccionIP(String direccionIP) throws DireccionIPRepetidaException {
        if(this.direccionesIP.contains(direccionIP))
            throw new DireccionIPRepetidaException("El equipo ya posee esta direccion IP.");

        this.direccionesIP.add(direccionIP);
        return direccionIP;
    }

    /**
     * Agrega un puerto al equipo si no existe ya uno igual.
     *
     * @param cantidad   La cantidad de puertos a agregar.
     * @param tipoPuerto El tipo de puerto a agregar.
     * @return El puerto agregado, o null si ya existe.
     */
    public Puerto agregarPuerto(int cantidad, TipoPuerto tipoPuerto) {
        Puerto puerto = new Puerto(cantidad, tipoPuerto);
        if (this.puertos.contains(puerto))
            throw new PuertoRepetidoException("El equipo ya posee este puerto.");

        this.puertos.add(puerto);
        this.mapaPuertos.put(tipoPuerto.getCodigo(), cantidad);
        return puerto;
    }

    public List<TipoPuerto> getTipoPuertos () {
        List<TipoPuerto> listaTipoPuertos = new ArrayList<TipoPuerto>();

        for(Puerto puerto : this.getPuertos()) { listaTipoPuertos.add(puerto.getTipoPuerto()); }

        return  listaTipoPuertos;
    }

    public List<Integer> getPuertosCantidades() {
        List<Integer> listaPuertosCantidades = new ArrayList<>();

        for (Puerto puerto : this.getPuertos()) { listaPuertosCantidades.add(puerto.getCantidad()); }

        return listaPuertosCantidades;
    }

    /**
     * Clase interna que representa un puerto en el equipo.
     */
    private class Puerto {
        private int cantidad;
        private TipoPuerto tipoPuerto;

        public Puerto(int cantidad, TipoPuerto tipoPuerto) {
            this.cantidad = cantidad;
            this.tipoPuerto = tipoPuerto;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

        public TipoPuerto getTipoPuerto() {
            return tipoPuerto;
        }

        public void setTipoPuerto(TipoPuerto tipoPuerto) {
            this.tipoPuerto = tipoPuerto;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Puerto puerto = (Puerto) o;
            return getCantidad() == puerto.getCantidad() && this.getTipoPuerto().equals(puerto.getTipoPuerto());
        }

        @Override
        public String toString() {
            return "Puerto{" +
                    "cantidad=" + cantidad +
                    ", tipoPuerto=" + tipoPuerto +
                    '}';
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public List<String> getDireccionesIP() {
        return direccionesIP;
    }

    public void setDireccionesIP(List<String> direccionesIP) {
        this.direccionesIP = direccionesIP;
    }

    public List<Puerto> getPuertos() {
        return puertos;
    }

    public void setPuertos(List<Puerto> puertos) {
        this.puertos = puertos;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public TipoEquipo getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(TipoEquipo tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", direccionesIP=" + direccionesIP +
                ", puertos=" + puertos +
                ", ubicacion=" + ubicacion +
                ", tipoEquipo=" + tipoEquipo +
                ", estado=" + estado +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipo equipo = (Equipo) o;
        return codigo.equals(equipo.getCodigo());
    }

}
