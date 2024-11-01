package servicio;

import modelo.TipoCable;
import java.util.List;

/**
 * La interfaz TipoCableService define los métodos para realizar operaciones CRUD
 * (Crear, Leer, Actualizar, Borrar) en los tipos de cable.
 */
public interface TipoCableService {

    /**
     * Inserta un nuevo tipo de cable.
     *
     * @param tipoCable El tipo de cable a insertar.
     */
    void insertar(TipoCable tipoCable);


    /**
     * Borra un tipo de cable.
     *
     * @param tipoCable El tipo de cable a borrar.
     */
    void borrar(TipoCable tipoCable);

    /**
     * Busca todos los tipos de cable.
     *
     * @return Una lista con todos los tipos de cable.
     */
    List<TipoCable> buscarTodos();
}
