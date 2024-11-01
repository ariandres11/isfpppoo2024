package dao;

import modelo.*;


import java.util.Hashtable;
import java.util.List;

/**
 * La interfaz EquipoDAO define los métodos para realizar operaciones CRUD
 * (Crear, Leer, Actualizar, Borrar) en los equipos.
 */
public interface EquipoDAO {

    /**
     * Inserta un nuevo equipo.
     *
     * @param equipo El equipo a insertar.
     */
    void insertar(Equipo equipo);

    /**
     * Actualiza un equipo existente.
     *
     * @param equipo El equipo a actualizar.
     */
    void actualizar(Equipo equipo);

    /**
     * Borra un equipo.
     *
     * @param equipo El equipo a borrar.
     */
    void borrar(Equipo equipo);

    /**
     * Busca todos los equipos.
     *
     * @return Una lista con todos los equipos.
     */
    List<Equipo> buscarTodos();

    /**
     * Carga los tipos de puerto desde la fuente de datos.
     *
     * @return Una lista con todos los tipos de puerto.
     */
    Hashtable<String, TipoPuerto>  cargarTiposPuerto();

    /**
     * Carga los tipos de equipo desde la fuente de datos.
     *
     * @return Una lista con todos los tipos de equipo.
     */
    Hashtable<String,TipoEquipo> cargarTiposEquipo();

    /**
     * Carga las ubicaciones desde la fuente de datos.
     *
     * @return Una lista con todas las ubicaciones.
     */
    Hashtable<String,Ubicacion> cargarUbicaciones();
}
