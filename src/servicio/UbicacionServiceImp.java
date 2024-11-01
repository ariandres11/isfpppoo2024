package servicio;

import dao.UbicacionDAO;
import modelo.Ubicacion;
import conexion.Factory;

import java.util.List;

/**
 * La clase UbicacionServiceImp implementa la interfaz UbicacionService
 * y proporciona la lógica para las operaciones CRUD sobre las ubicaciones.
 */
public class UbicacionServiceImp implements UbicacionService {

    private UbicacionDAO ubicacionDAO;

    /**
     * Constructor de la clase UbicacionServiceImp.
     * Inicializa un nuevo objeto de UbicacionSecuencialDAO.
     */
    public UbicacionServiceImp() {
        ubicacionDAO = (UbicacionDAO) Factory.getInstancia("ubicaciones");
    }

    /**
     * Inserta una nueva ubicación utilizando el DAO.
     *
     * @param ubicacion La ubicación a insertar.
     */
    @Override
    public void insertar(Ubicacion ubicacion) {
        ubicacionDAO.insertar(ubicacion);
    }

    /**
     * Actualiza una ubicación existente utilizando el DAO.
     *
     * @param ubicacion La ubicación a actualizar.
     */
    @Override
    public void actualizar(Ubicacion ubicacion) {
        ubicacionDAO.actualizar(ubicacion);
    }

    /**
     * Borra una ubicación utilizando el DAO.
     *
     * @param ubicacion La ubicación a borrar.
     */
    @Override
    public void borrar(Ubicacion ubicacion) {
        ubicacionDAO.borrar(ubicacion);
    }

    /**
     * Busca todas las ubicaciones utilizando el DAO.
     *
     * @return Una lista con todas las ubicaciones.
     */
    @Override
    public List<Ubicacion> buscarTodos() {
        return ubicacionDAO.buscarTodos();
    }
}
