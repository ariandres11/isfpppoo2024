package servicio;

import modelo.TipoPuerto;
import java.util.List;

/**
 * La interfaz TipoPuertoService define los métodos para realizar operaciones CRUD
 * (Crear, Leer, Actualizar, Borrar) en los tipos de puerto.
 */
public interface TipoPuertoService {

    /**
     * Inserta un nuevo tipo de puerto.
     *
     * @param tipoPuerto El tipo de puerto a insertar.
     */
    void insertar(TipoPuerto tipoPuerto);


    /**
     * Borra un tipo de puerto.
     *
     * @param tipoPuerto El tipo de puerto a borrar.
     */
    void borrar(TipoPuerto tipoPuerto);

    /**
     * Busca todos los tipos de puerto.
     *
     * @return Una lista con todos los tipos de puerto.
     */
    List<TipoPuerto> buscarTodos();
}
