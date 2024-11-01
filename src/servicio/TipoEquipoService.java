package servicio;

import modelo.TipoEquipo;
import java.util.List;

/**
 * La interfaz TipoEquipoService define los métodos para realizar operaciones CRUD
 * (Crear, Leer, Actualizar, Borrar) en los tipos de equipo.
 */
public interface TipoEquipoService {

    /**
     * Inserta un nuevo tipo de equipo.
     *
     * @param tipoEquipo El tipo de equipo a insertar.
     */
    void insertar(TipoEquipo tipoEquipo);

    /**
     * Actualiza un tipo de equipo existente.
     *
     * @param tipoEquipo El tipo de equipo a actualizar.
     */
    void actualizar(TipoEquipo tipoEquipo);

    /**
     * Borra un tipo de equipo.
     *
     * @param tipoEquipo El tipo de equipo a borrar.
     */
    void borrar(TipoEquipo tipoEquipo);

    /**
     * Busca todos los tipos de equipo.
     *
     * @return Una lista con todos los tipos de equipo.
     */
    List<TipoEquipo> buscarTodos();
}
