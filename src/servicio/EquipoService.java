package servicio;

import modelo.Equipo;
import java.util.List;

/**
 * La interfaz EquipoService define los métodos para realizar operaciones CRUD
 * (Crear, Leer, Actualizar, Borrar) en los equipos.
 */
public interface EquipoService {

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
}
