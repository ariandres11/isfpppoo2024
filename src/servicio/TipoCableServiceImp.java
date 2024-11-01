package servicio;

import dao.TipoCableDAO;
import modelo.TipoCable;
import conexion.Factory;

import java.util.List;

/**
 * La clase TipoCableServiceImp implementa la interfaz TipoCableService
 * y proporciona la lógica para las operaciones CRUD sobre los tipos de cable.
 */
public class TipoCableServiceImp implements TipoCableService {

    private TipoCableDAO tipoCableDAO;

    /**
     * Constructor de la clase TipoCableServiceImp.
     * Inicializa un nuevo objeto de TipoCableSecuencialDAO.
     */
    public TipoCableServiceImp() {
        tipoCableDAO = (TipoCableDAO) Factory.getInstancia("tiposCable");
    }

    /**
     * Inserta un nuevo tipo de cable utilizando el DAO.
     *
     * @param tipoCable El tipo de cable a insertar.
     */
    @Override
    public void insertar(TipoCable tipoCable) {
        tipoCableDAO.insertar(tipoCable);
    }

    /**
     * Borra un tipo de cable utilizando el DAO.
     *
     * @param tipoCable El tipo de cable a borrar.
     */
    @Override
    public void borrar(TipoCable tipoCable) {
        tipoCableDAO.borrar(tipoCable);
    }

    /**
     * Busca todos los tipos de cable utilizando el DAO.
     *
     * @return Una lista con todos los tipos de cable.
     */
    @Override
    public List<TipoCable> buscarTodos() {
        return tipoCableDAO.buscarTodos();
    }
}
