package servicio;

import dao.TipoPuertoDAO;
import modelo.TipoPuerto;
import conexion.Factory;

import java.util.List;

/**
 * La clase TipoPuertoServiceImp implementa la interfaz TipoPuertoService
 * y proporciona la lógica para las operaciones CRUD sobre los tipos de puerto.
 */
public class TipoPuertoServiceImp implements TipoPuertoService {

    private TipoPuertoDAO tipoPuertoDAO;

    /**
     * Constructor de la clase TipoPuertoServiceImp.
     * Inicializa un nuevo objeto de TipoPuertoSecuencialDAO.
     */
    public TipoPuertoServiceImp() {
        tipoPuertoDAO = (TipoPuertoDAO) Factory.getInstancia("tiposPuerto");
    }

    /**
     * Inserta un nuevo tipo de puerto utilizando el DAO.
     *
     * @param tipoPuerto El tipo de puerto a insertar.
     */
    @Override
    public void insertar(TipoPuerto tipoPuerto) {
        tipoPuertoDAO.insertar(tipoPuerto);
    }


    /**
     * Borra un tipo de puerto utilizando el DAO.
     *
     * @param tipoPuerto El tipo de puerto a borrar.
     */
    @Override
    public void borrar(TipoPuerto tipoPuerto) {
        tipoPuertoDAO.borrar(tipoPuerto);
    }

    /**
     * Busca todos los tipos de puerto utilizando el DAO.
     *
     * @return Una lista con todos los tipos de puerto.
     */
    @Override
    public List<TipoPuerto> buscarTodos() {
        return tipoPuertoDAO.buscarTodos();
    }
}
