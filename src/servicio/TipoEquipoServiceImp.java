package servicio;

import dao.TipoEquipoDAO;
import modelo.TipoEquipo;
import conexion.Factory;

import java.util.List;

/**
 * La clase TipoEquipoServiceImp implementa la interfaz TipoEquipoService
 * y proporciona la lógica para las operaciones CRUD sobre los tipos de equipo.
 */
public class TipoEquipoServiceImp implements TipoEquipoService {

    private TipoEquipoDAO tipoEquipoDAO;

    /**
     * Constructor de la clase TipoEquipoServiceImp.
     * Inicializa un nuevo objeto de TipoEquipoSecuencialDAO.
     */
    public TipoEquipoServiceImp() {
        tipoEquipoDAO = (TipoEquipoDAO) Factory.getInstancia("tiposEquipo");
    }

    /**
     * Inserta un nuevo tipo de equipo utilizando el DAO.
     *
     * @param tipoEquipo El tipo de equipo a insertar.
     */
    @Override
    public void insertar(TipoEquipo tipoEquipo) {
        tipoEquipoDAO.insertar(tipoEquipo);
    }

    /**
     * Actualiza un tipo de equipo existente utilizando el DAO.
     *
     * @param tipoEquipo El tipo de equipo a actualizar.
     */
    @Override
    public void actualizar(TipoEquipo tipoEquipo) {
        tipoEquipoDAO.actualizar(tipoEquipo);
    }

    /**
     * Borra un tipo de equipo utilizando el DAO.
     *
     * @param tipoEquipo El tipo de equipo a borrar.
     */
    @Override
    public void borrar(TipoEquipo tipoEquipo) {
        tipoEquipoDAO.borrar(tipoEquipo);
    }

    /**
     * Busca todos los tipos de equipo utilizando el DAO.
     *
     * @return Una lista con todos los tipos de equipo.
     */
    @Override
    public List<TipoEquipo> buscarTodos() {
        return tipoEquipoDAO.buscarTodos();
    }
}
