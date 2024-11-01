package servicio;

import dao.EquipoDAO;
import modelo.Equipo;
import conexion.Factory;

import java.util.List;

/**
 * La clase EquipoServiceImp implementa la interfaz EquipoService
 * y proporciona la lógica para las operaciones CRUD sobre los equipos.
 */
public class EquipoServiceImp implements EquipoService {

    private EquipoDAO equipoDAO;

    /**
     * Constructor de la clase EquipoServiceImp.
     * Inicializa un nuevo objeto de EquipoSecuencialDAO.
     */
    public EquipoServiceImp() {
        equipoDAO = (EquipoDAO) Factory.getInstancia("equipos");
    }

    /**
     * Inserta un nuevo equipo utilizando el DAO.
     *
     * @param equipo El equipo a insertar.
     */
    @Override
    public void insertar(Equipo equipo) {
        equipoDAO.insertar(equipo);
    }

    /**
     * Actualiza un equipo existente utilizando el DAO.
     *
     * @param equipo El equipo a actualizar.
     */
    @Override
    public void actualizar(Equipo equipo) {
        equipoDAO.actualizar(equipo);
    }

    /**
     * Borra un equipo utilizando el DAO.
     *
     * @param equipo El equipo a borrar.
     */
    @Override
    public void borrar(Equipo equipo) {
        equipoDAO.borrar(equipo);
    }

    /**
     * Busca todos los equipos utilizando el DAO.
     *
     * @return Una lista con todos los equipos.
     */
    @Override
    public List<Equipo> buscarTodos() {
        return equipoDAO.buscarTodos();
    }
}
