package servicio;

import modelo.Conexion;
import java.util.List;

/**
 * La interfaz ConexionService define los métodos para realizar operaciones CRUD
 * (Crear, Leer, Actualizar, Borrar) en las conexiones.
 */
public interface ConexionService {

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
    List<Conexion> buscarTodos();
}
