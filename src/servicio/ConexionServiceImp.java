package servicio;

import dao.ConexionDAO;
import modelo.Conexion;
import conexion.Factory;

import java.util.List;

/**
 * La clase ConexionServiceImp implementa la interfaz ConexionService
 * y proporciona la lógica para las operaciones CRUD sobre las conexiones.
 */
public class ConexionServiceImp implements ConexionService {

    private ConexionDAO conexionDAO;

    /**
     * Constructor de la clase ConexionServiceImp.
     * Inicializa un nuevo objeto de ConexionSecuencialDAO.
     */
    public ConexionServiceImp() {
        conexionDAO = (ConexionDAO) Factory.getInstancia("conexiones");
    }

    /**
     * Inserta una nueva conexión utilizando el DAO.
     *
     * @param conexion La conexión a insertar.
     */
    @Override
    public void insertar(Conexion conexion) {
        conexionDAO.insertar(conexion);
    }

    /**
     * Borra una conexión utilizando el DAO.
     *
     * @param conexion La conexión a borrar.
     */
    @Override
    public void borrar(Conexion conexion) {
        conexionDAO.borrar(conexion);
    }

    /**
     * Busca todas las conexiones utilizando el DAO.
     *
     * @return Una lista con todas las conexiones.
     */
    @Override
    public List<Conexion> buscarTodos() {
        return conexionDAO.buscarTodas();
    }
}
