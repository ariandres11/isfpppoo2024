package dao;

import modelo.Conexion;
import modelo.Equipo;
import modelo.TipoCable;

import java.util.Hashtable;
import java.util.List;

/**
 * La interfaz ConexionDAO define los métodos para realizar operaciones CRUD
 * (Crear, Leer, Actualizar, Borrar) en las conexiones.
 */
public interface ConexionDAO {

    /**
     * Inserta una nueva conexión.
     *
     * @param conexion La conexión a insertar.
     */
    void insertar(Conexion conexion);

    /**
     * Borra una conexión.
     *
     * @param conexion La conexión a borrar.
     */
    void borrar(Conexion conexion);

    /**
     * Busca todas las conexiones.
     *
     * @return Una lista con todas las conexiones.
     */
    List<Conexion> buscarTodas();

    /**
     * Carga los equipos desde la fuente de datos.
     *
     * @return Una lista con todos los equipos.
     */
    Hashtable<String,Equipo> cargarEquipos();

    /**
     * Carga los tipos de cable desde la fuente de datos.
     *
     * @return Una tabla hash con los tipos de cable.
     */
    Hashtable<String,TipoCable> cargarTiposCable();
}
