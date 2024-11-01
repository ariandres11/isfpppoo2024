package servicio;

import modelo.Ubicacion;
import java.util.List;

/**
 * La interfaz UbicacionService define los métodos para realizar operaciones CRUD
 * (Crear, Leer, Actualizar, Borrar) en las ubicaciones.
 */
public interface UbicacionService {

    /**
     * Inserta una nueva ubicación.
     *
     * @param ubicacion La ubicación a insertar.
     */
    void insertar(Ubicacion ubicacion);

    /**
     * Actualiza una ubicación existente.
     *
     * @param ubicacion La ubicación a actualizar.
     */
    void actualizar(Ubicacion ubicacion);

    /**
     * Borra una ubicación.
     *
     * @param ubicacion La ubicación a borrar.
     */
    void borrar(Ubicacion ubicacion);

    /**
     * Busca todas las ubicaciones.
     *
     * @return Una lista con todas las ubicaciones.
     */
    List<Ubicacion> buscarTodos();
}
