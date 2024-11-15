package datos;

import dao.EquipoDAO;
import dao.UbicacionDAO;
import dao.ConexionDAO;
import dao.TipoEquipoDAO;
import dao.TipoPuertoDAO;
import dao.TipoCableDAO;

import dao.postgresql.TipoCablePostgreDAO;
import dao.postgresql.TipoPuertoPostgreDAO;
import dao.postgresql.TipoEquipoPostgreDAO;
import dao.postgresql.EquipoPostgreDAO;
import dao.postgresql.UbicacionPostgreDAO;
import dao.postgresql.ConexionPostgreDAO;

import dao.secuencial.EquipoSecuencialDAO;
import dao.secuencial.UbicacionSecuencialDAO;
import dao.secuencial.ConexionSecuencialDAO;
import dao.secuencial.TipoCableSecuencialDAO;
import dao.secuencial.TipoEquipoSecuencialDAO;
import dao.secuencial.TipoPuertoSecuencialDAO;

import modelo.Equipo;
import modelo.Conexion;
import modelo.Ubicacion;
import modelo.TipoEquipo;
import modelo.TipoPuerto;
import modelo.TipoCable;

import org.apache.log4j.Logger;

public class FileTextBD {
    private static final Logger logger = Logger.getLogger(FileTextBD.class);

    public static void main(String[] args) {
        logger.debug("Iniciando la migración de datos desde secuencial a PostgreSQL.");

        // Migración de TipoEquipo
        TipoEquipoDAO tipoEquipoSecuencialDAO = new TipoEquipoSecuencialDAO();
        TipoEquipoDAO tipoEquipoPostgreDAO = new TipoEquipoPostgreDAO();
        for (TipoEquipo t : tipoEquipoSecuencialDAO.buscarTodos()) {
            tipoEquipoPostgreDAO.insertar(t);
            logger.info("TipoEquipo insertado: " + t);
        }

        // Migración de TipoCable
        TipoCableDAO tipoCableSecuencialDAO = new TipoCableSecuencialDAO();
        TipoCableDAO tipoCablePostgreDAO = new TipoCablePostgreDAO();
        for (TipoCable t : tipoCableSecuencialDAO.buscarTodos()) {
            tipoCablePostgreDAO.insertar(t);
            logger.info("TipoCable insertado: " + t);
        }

        // Migración de TipoPuerto
        TipoPuertoDAO tipoPuertoSecuencialDAO = new TipoPuertoSecuencialDAO();
        TipoPuertoDAO tipoPuertoPostgreDAO = new TipoPuertoPostgreDAO();
        for (TipoPuerto t : tipoPuertoSecuencialDAO.buscarTodos()) {
            tipoPuertoPostgreDAO.insertar(t);
            logger.info("TipoPuerto insertado: " + t);
        }

        // Migración de Ubicacion
        UbicacionDAO ubicacionPostgresqlDAO = new UbicacionPostgreDAO();
        UbicacionDAO ubicacionSecuencialDAO = new UbicacionSecuencialDAO();
        for (Ubicacion u : ubicacionSecuencialDAO.buscarTodos()) {
            ubicacionPostgresqlDAO.insertar(u);
            logger.info("Ubicacion insertada: " + u);
        }

        // Migración de Equipo
        EquipoDAO equipoSecuencialDAO = new EquipoSecuencialDAO();
        EquipoDAO equipoPostgresqlDAO = new EquipoPostgreDAO();
        for (Equipo e : equipoSecuencialDAO.buscarTodos()) {
            equipoPostgresqlDAO.insertar(e);
            logger.info("Equipo insertado: " + e);
        }

        // Migración de Conexion
        ConexionDAO conexionSecuencialDAO = new ConexionSecuencialDAO();
        ConexionDAO conexionPostgresqlDAO = new ConexionPostgreDAO();
        for (Conexion c : conexionSecuencialDAO.buscarTodas()) {
            conexionPostgresqlDAO.insertar(c);
            logger.info("Conexion insertada: " + c);
        }

        logger.info("Migración de datos completada.");
    }
}
