package modelo;

import excepciones.*;
import servicio.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * La clase Red representa una red de computadoras,
 * gestionando ubicaciones, equipos y conexiones dentro de la red.
 */
public class Red {

    private static Red red = null;
    private String nombre;
    private List<Ubicacion> ubicaciones;
    private UbicacionService ubicacionService;
    private List<Equipo> equipos;
    private EquipoService equipoService;
    private List<Conexion> conexiones;
    private ConexionService conexionService;
    private TipoPuertoService tipoPuertoService;
    private TipoCableService tipoCableService;
    private TipoEquipoService tipoEquipoService;

    /**
     * Obtiene la instancia única de la clase Red.
     *
     * @return La instancia de la red.
     */
    public static Red getRed() {
        if (red == null) {
            red = new Red("Red de computadoras");
        }
        return red;
    }

    /**
     * Constructor que inicializa una nueva red con el nombre dado.
     *
     * @param nombre El nombre de la red.
     */
    public Red(String nombre) {
        super();
        this.nombre = nombre;
        this.ubicaciones = new ArrayList<>();
        this.ubicacionService = new UbicacionServiceImp();
        this.ubicaciones.addAll(ubicacionService.buscarTodos());
        this.equipos = new ArrayList<>();
        this.equipoService = new EquipoServiceImp();
        this.equipos.addAll(equipoService.buscarTodos());
        this.conexiones = new ArrayList<>();
        this.conexionService = new ConexionServiceImp();
        this.conexiones.addAll(conexionService.buscarTodos());
        this.tipoPuertoService = new TipoPuertoServiceImp();
        this.tipoCableService = new TipoCableServiceImp();
        this.tipoEquipoService = new TipoEquipoServiceImp();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos para insertar nuevos elementos en la red

    /**
     * Agrega una nueva conexión a la red.
     *
     * @param conexion La conexión a agregar.
     * @return La conexión agregada o null si ya existe.
     */
    public Conexion agregarConexion(Conexion conexion) throws ConexionRepetidaException {
        if (this.conexiones.contains(conexion))
            throw new ConexionRepetidaException("Esta red ya posee a esta conexion.");

        this.conexiones.add(conexion);
        this.conexionService.insertar(conexion);
        return conexion;

    }

    /**
     * Agrega una nueva ubicación a la red.
     *
     * @param ubicacion La ubicación a agregar.
     * @return La ubicación agregada o null si ya existe.
     */
    public Ubicacion agregarUbicacion(Ubicacion ubicacion) throws UbicacionRepetidaException {
        if (this.ubicaciones.contains(ubicacion))
            throw new UbicacionRepetidaException("Esta red ya posee a esta ubicacion.");

        this.ubicaciones.add(ubicacion);
        this.ubicacionService.insertar(ubicacion);
        return ubicacion;

    }

    /**
     * Agrega un nuevo equipo a la red.
     *
     * @param equipo El equipo a agregar.
     * @return El equipo agregado o null si ya existe.
     */
    public Equipo agregarEquipo(Equipo equipo) throws EquipoRepetidoException {
        if (this.equipos.contains(equipo))
            throw new EquipoRepetidoException("Esta red ya posee a este equipo.");

        this.equipos.add(equipo);
        this.equipoService.insertar(equipo);
        return equipo;

    }

    /**
     * Agrega un nuevo tipo de puerto a la red.
     * @param tipoPuerto El tipo de puerto a agregar.
     * @return El tipo de puerto agregado.
     */
    public TipoPuerto agregarTipoPuerto(TipoPuerto tipoPuerto) throws TipoPuertoRepetidoException {
        if(this.tipoPuertoService.buscarTodos().contains(tipoPuerto))
            throw new TipoPuertoRepetidoException("Este tipo de equipo ya existe.");

        this.tipoPuertoService.insertar(tipoPuerto);
        return tipoPuerto;
    }
    /**
     * Agrega un nuevo tipo de equipo a la red.
     * @param tipoEquipo El tipo de equipo a agregar.
     * @return El tipo de equipo agregado.
     */
    public TipoEquipo agregarTipoEquipo(TipoEquipo tipoEquipo) throws TipoEquipoRepetidoException {
        if(this.tipoEquipoService.buscarTodos().contains(tipoEquipo))
            throw new TipoEquipoRepetidoException("Este tipo de equipo ya existe.");

        this.tipoEquipoService.insertar(tipoEquipo);
        return tipoEquipo;
    }
    /**
     * Agrega un nuevo tipo de cable a la red.
     * @param tipoCable El tipo de cable a agregar.
     * @return El tipo de cable agregado.
     */
    public TipoCable agregarTipoCable(TipoCable tipoCable) throws TipoCableRepetidoException {
        if(this.tipoCableService.buscarTodos().contains(tipoCable))
            throw new TipoCableRepetidoException("Este tipo de cable ya existe");

        this.tipoCableService.insertar(tipoCable);
        return tipoCable;
    }

    // Métodos para borrar elementos de la red

    /**
     * Elimina un equipo de la red.
     *
     * @param equipo El equipo a eliminar.
     */
    public void borrarEquipo(Equipo equipo) throws EquipoNoExistenteException {
        if (!this.equipos.contains(equipo))
            throw new EquipoNoExistenteException("Este equipo no existe en la red.");
        for(Conexion conexion : conexiones) {
            if(conexion.getEquipo1().equals(equipo) || conexion.getEquipo2().equals(equipo))
                throw new EquipoEnUsoException("Existen conexiones con este equipo. Elimine las conexiones antes de continuar.");
        }

        this.equipos.remove(equipo);
        this.equipoService.borrar(equipo);
    }

    /**
     * Elimina una ubicación de la red.
     *
     * @param ubicacion La ubicación a eliminar.
     */
    public void borrarUbicacion(Ubicacion ubicacion) throws UbicacionNoExistenteException ,
                                                            UbicacionEnUsoException {
        if (!this.ubicaciones.contains(ubicacion))
            throw new UbicacionNoExistenteException("Esta ubicacion no existe en la red.");

        for(Equipo equipo : equipos) {
            if(equipo.getUbicacion().equals(ubicacion))
                throw new UbicacionEnUsoException("Existen equipos en la ubicacion: " + ubicacion.getCodigo() +
                        "(" + ubicacion.getDescripcion() +
                        "). Elimine aquellos equipos antes de continuar.");
        }

        this.ubicaciones.remove(ubicacion);
        this.ubicacionService.borrar(ubicacion);
    }

    /**
     * Elimina una conexión de la red.
     *
     * @param conexion La conexión a eliminar.
     */
    public void borrarConexion(Conexion conexion) throws ConexionNoExistenteException{
        if (!this.conexiones.contains(conexion))
            throw new ConexionNoExistenteException("Esta conexion no existe en la red.");

        this.conexiones.remove(conexion);
        this.conexionService.borrar(conexion);
    }
    public void borrarTipoPuerto(TipoPuerto tipoPuerto) throws TipoPuertoNoExistenteException,
                                                                TipoPuertoEnUsoException {
        if(!this.tipoPuertoService.buscarTodos().contains(tipoPuerto))
            throw new TipoPuertoNoExistenteException("Este tipo de puerto no existe en la red.");

        for (Equipo equipo : equipos) {
            for(TipoPuerto tipoPuerto1 : equipo.getTipoPuertos()) {
                if(tipoPuerto1.equals(tipoPuerto1))
                    throw new TipoPuertoEnUsoException("Existen equipos que poseen el tipo de puerto: " +
                            tipoPuerto.getCodigo() + "(" + tipoPuerto.getDescripcion() +
                            "). Elimine aquellos equipos antes de continuar." );
            }
        }

        this.tipoPuertoService.borrar(tipoPuerto);
    }

    public void borrarTipoEquipo(TipoEquipo tipoEquipo) throws TipoEquipoNoExistenteException,
                                                                TipoEquipoEnUsoException{
        if(!this.tipoEquipoService.buscarTodos().contains(tipoEquipo))
            throw new TipoEquipoNoExistenteException("Este tipo de equipo no existe en la red");

        for (Equipo equipo : equipos) {
            if (equipo.getTipoEquipo().equals(tipoEquipo))
                throw new TipoEquipoEnUsoException("Existen equipos que poseen el tipo de equipo: " +
                        tipoEquipo.getCodigo() + "(" + tipoEquipo.getDescripcion() +
                        "). Elimine aquellos equipos antes de continuar");
        }

        this.tipoEquipoService.borrar(tipoEquipo);
    }

    public void borrarTipoCable(TipoCable tipoCable) throws TipoCableNoExistenteException,
                                                            TipoCableEnUsoException{
        if(!this.tipoCableService.buscarTodos().contains(tipoCable))
            throw new TipoCableNoExistenteException("Este tipo de cable no existe en la red.");

        for (Conexion conexion : conexiones) {
            if(conexion.getTipoCable().equals(tipoCable))
                throw new TipoCableEnUsoException("Existen conexiones que poseen el tipo de cable: " +
                        tipoCable.getCodigo() + "(" + tipoCable.getDescripcion() +
                        "). Elimine aquellas conexiones antes de continuar.");
        }

        this.tipoCableService.borrar(tipoCable);
    }

    // Métodos para modificar elementos de la red

    /**
     * Modifica un equipo existente en la red.
     * Implementación del patrón Singleton.
     * @param equipo El equipo a modificar.
     */
    public void modificarEquipo(Equipo equipo) {
        int pos = equipos.indexOf(equipo);
        equipos.set(pos, equipo);
        equipoService.actualizar(equipo);
    }

    /**
     * Modifica una ubicación existente en la red.
     *
     * @param ubicacion La ubicación a modificar.
     */
    public void modificarUbicacion(Ubicacion ubicacion) {
        int pos = ubicaciones.indexOf(ubicacion);
        ubicaciones.set(pos, ubicacion);
        ubicacionService.actualizar(ubicacion);
    }

    /**
     * Busca a un equipo de la red por su codigo.
     *
     * @param codigoEquipo Codigo del equipo a buscar.
     * @return El equipo correspondiente a codigoEquipo, retorna null si no existe.
     */
    public Equipo buscarEquipo(String codigoEquipo) {
        for(Equipo equipo : getEquipos()) {
            if (Objects.equals(equipo.getCodigo(), codigoEquipo)) {
                return equipo;
            }
        }
        return null;
    }

    /**
     * Busca a una conexion de la red por su codigo.
     *
     * @param codigoEquipo1 Codigo del equipo1.
     * @param codigoEquipo2 Codigo del equipo2.
     * @param codigoCable Codigo del TipoCable.
     * @return La conexion correspondiente a codigoEquipo1, codigoEquipo2 y codigoCable, retorna null si no existe.
     */
    public Conexion buscarConexion(String codigoEquipo1, String codigoEquipo2, String codigoCable) {

        for (Conexion conexion : getConexiones()) {
            if (Objects.equals(conexion.getEquipo1Codigo(), codigoEquipo1) &&
                    Objects.equals(conexion.getEquipo2Codigo(), codigoEquipo2) &&
                    Objects.equals(conexion.getTipoCableCodigo(), codigoCable)) {
                return conexion;
            }
        }
        return null;
    }

    /**
     * Busca a una ubicacion de la red por su codigo.
     *
     * @param codigoUbicacion Codigo de la ubicacion.
     * @return La ubicacion correspondiente a codigoUbicacion, retorna null si no existe.
     */
    public Ubicacion buscarUbicacion (String codigoUbicacion) {
        for (Ubicacion ubicacion : getUbicaciones()) {
            if(Objects.equals(ubicacion.getCodigo(), codigoUbicacion)) {
                return ubicacion;
            }
        }
        return null;
    }

    /**
     * Busca a un TipoEquipo de la red por su codigo.
     *
     * @param codigoTipoEquipo Codigo del TipoEquipo.
     * @return El tipoEquipo correspondiente a codigoTipoEquipo, retorna null si no existe.
     */
    public TipoEquipo buscarTipoEquipo (String codigoTipoEquipo) {
        for(TipoEquipo tipoEquipo : tipoEquipoService.buscarTodos()) {
            if(Objects.equals(tipoEquipo.getCodigo(), codigoTipoEquipo)) {
                return tipoEquipo;
            }
        }
        return null;
    }

    /**
     * Busca a un TipoPuerto de la red por su codigo.
     *
     * @param codigoTipoPuerto Codigo del TipoPuerto.
     * @return El tipoPuerto correspondiente a codigoTipoPuerto, retorna null si no existe.
     */
    public TipoPuerto buscarTipoPuerto (String codigoTipoPuerto) {
        for (TipoPuerto tipoPuerto : tipoPuertoService.buscarTodos()) {
            if(Objects.equals(tipoPuerto.getCodigo(), codigoTipoPuerto)) {
                return tipoPuerto;
            }
        }
        return null;
    }

    /**
     * Busca a un TipoCable de la red por su codigo.
     *
     * @param codigoTipoCable codigo del TipoCable.
     * @return El tipoCable correspondiente a codigoTipoCable, retorna null si no existe.
     */
    public TipoCable buscarTipoCable (String codigoTipoCable) {
        for (TipoCable tipoCable : tipoCableService.buscarTodos()) {
            if (Objects.equals(tipoCable.getCodigo(), codigoTipoCable)) {
                return tipoCable;
            }
        }
        return null;
    }

    public List<String> getEquiposCodigos() {
        List<String> equiposCodigos = new ArrayList<String >();
        for(Equipo equipo : getEquipos())
            equiposCodigos.add(equipo.getCodigo());
        return equiposCodigos;
    }


    public List<Ubicacion> getUbicaciones() {
        return ubicaciones;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public List<Conexion> getConexiones() {
        return conexiones;
    }

    public List<TipoPuerto> getTipoPuertos() { return tipoPuertoService.buscarTodos(); }

    public List<TipoCable> getTipoCables() { return tipoCableService.buscarTodos(); }

    public List<TipoEquipo> getTipoEquipos() { return tipoEquipoService.buscarTodos(); }

    @Override
    public String toString() {
        return "Red{" +
                "nombre='" + nombre + '\'' +
                ", ubicaciones=" + ubicaciones +
                ", ubicacionService=" + ubicacionService +
                ", equipos=" + equipos +
                ", equipoService=" + equipoService +
                ", conexiones=" + conexiones +
                ", conexionService=" + conexionService +
                '}';
    }
}
