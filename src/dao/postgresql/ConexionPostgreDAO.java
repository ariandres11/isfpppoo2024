package dao.postgresql;

import conexion.BDConexion;
import dao.ConexionDAO;
import modelo.*;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.*;

public class ConexionPostgreDAO implements ConexionDAO {
    private static final Logger logger = Logger.getLogger(ConexionPostgreDAO.class);
    private Hashtable<String, Equipo> tablaEquipos;
    private Hashtable<String, TipoCable> tablaTiposCable;
    private Hashtable<String, Ubicacion> tablaUbicaciones;
    private Hashtable<String, TipoEquipo> tablaTiposEquipo;
    private Hashtable<String, TipoPuerto> tablaTiposPuerto;

    public ConexionPostgreDAO() {
        tablaTiposCable = cargarTiposCable();
        tablaUbicaciones = cargarUbicaciones();
        tablaTiposEquipo = cargarTiposEquipo();
        tablaTiposPuerto = cargarTiposPuerto();
        tablaEquipos = cargarEquipos();
    }

    private Hashtable<String, TipoPuerto> cargarTiposPuerto() {
        Hashtable<String, TipoPuerto> tabla = new Hashtable<>();
        String sql = "SELECT * FROM ari_tipos_puerto";
        try (Statement stmt = BDConexion.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TipoPuerto tipoPuerto = new TipoPuerto();
                tipoPuerto.setCodigo(rs.getString("codigo"));
                tipoPuerto.setDescripcion(rs.getString("descripcion"));
                tipoPuerto.setVelocidad(rs.getInt("velocidad"));
                tabla.put(rs.getString("codigo"), tipoPuerto);
            }
        } catch (SQLException e) {
            logger.error("Error al cargar tipos de puerto", e);
        }
        return tabla;
    }

    @Override
    public void insertar(Conexion conexion) {
        String sql = "INSERT INTO ari_conexiones (codigo_equipo1, codigo_equipo2, tipo_cable, tipo_puerto_equipo1, tipo_puerto_equipo2, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = BDConexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, conexion.getEquipo1().getCodigo());
            ps.setString(2, conexion.getEquipo2().getCodigo());
            //Cargar el tipo de cable de la tabla
            ps.setString(3, tablaTiposCable.get(conexion.getTipoCable().getCodigo()).getCodigo());
            ps.setString(4, tablaTiposPuerto.get(conexion.getEquipo1().getTipoPuertoInicial().getCodigo()).getCodigo());
            ps.setString(5, tablaTiposPuerto.get(conexion.getEquipo2().getTipoPuertoInicial().getCodigo()).getCodigo());
            ps.setBoolean(6, conexion.isEstado());
            ps.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error al insertar conexión", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Conexion> buscarTodas() {
        List<Conexion> listaConexiones = new ArrayList<>();
        String sql = "SELECT * FROM ari_conexiones";
        try (Statement stmt = BDConexion.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                logger.debug(rs);
                Conexion conexion = new Conexion();
                conexion.setEquipo1(tablaEquipos.get(rs.getString("codigo_equipo1")));
                conexion.setEquipo2(tablaEquipos.get(rs.getString("codigo_equipo2")));
                conexion.setTipoCable(tablaTiposCable.get(rs.getString("tipo_cable")));
                conexion.setTipoPuertoEquipo1(tablaTiposPuerto.get(rs.getString("tipo_puerto_equipo1")));
                conexion.setTipoPuertoEquipo2(tablaTiposPuerto.get(rs.getString("tipo_puerto_equipo2")));
                conexion.setEstado(rs.getBoolean("estado"));
                listaConexiones.add(conexion);
            }
        } catch (SQLException ex) {
            logger.error("Error al listar conexiones", ex);
            throw new RuntimeException(ex);
        }
        return listaConexiones;
    }
    @Override
    public void borrar(Conexion conexion) {
        String sql = "DELETE FROM ari_conexiones WHERE codigo_equipo1 = ? AND codigo_equipo2 = ?";
        try (PreparedStatement ps = BDConexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, conexion.getEquipo1().getCodigo());
            ps.setString(2, conexion.getEquipo2().getCodigo());
            ps.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error al borrar conexión", ex);
            throw new RuntimeException(ex);
        }
    }

    private void cargarPuertos(Equipo equipo) {
    String sql = "SELECT * FROM ari_equipos_puertos WHERE equipo_codigo  = ?";
    try (PreparedStatement ps = BDConexion.getConnection().prepareStatement(sql)) {
        ps.setString(1, equipo.getCodigo());
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                equipo.agregarPuerto(rs.getInt("cantidad_total"), tablaTiposPuerto.get(rs.getString("tipo_puerto_codigo")));
            }
        }
    } catch (SQLException ex) {
        logger.error("Error al cargar puertos de equipo", ex);
        throw new RuntimeException(ex);
    }
}
private void cargarIps(Equipo equipo) {
    String sql = "SELECT * FROM ari_ips WHERE equipo = ?";
    try (PreparedStatement ps = BDConexion.getConnection().prepareStatement(sql)) {
        ps.setString(1, equipo.getCodigo());
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                equipo.getDireccionesIP().add(rs.getString("ip"));
            }
        }
    } catch (SQLException ex) {
        logger.error("Error al cargar ips de equipo", ex);
        throw new RuntimeException(ex);
    }
}
@Override
public Hashtable<String, Equipo> cargarEquipos() {
    Hashtable<String, Equipo> tablaEquipos = new Hashtable<>();
    String sql = "SELECT * FROM ari_equipos";
    try (Statement stmt = BDConexion.getConnection().createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            Equipo equipo = new Equipo();
            equipo.setCodigo(rs.getString("codigo"));
            equipo.setDescripcion(rs.getString("descripcion"));
            equipo.setMarca(rs.getString("marca"));
            equipo.setModelo(rs.getString("modelo"));
            equipo.setUbicacion(cargarUbicaciones().get(rs.getString("ubicacion")));
            equipo.setTipoEquipo(cargarTiposEquipo().get(rs.getString("tipo_equipo")));
            equipo.setEstado(rs.getBoolean("estado"));
            cargarIps(equipo);
            cargarPuertos(equipo);
            tablaEquipos.put(equipo.getCodigo(), equipo);
        }
    } catch (SQLException ex) {
        logger.error("Error al cargar equipos", ex);
        throw new RuntimeException(ex);
    }
    return tablaEquipos;
}

    private Hashtable<String, TipoEquipo> cargarTiposEquipo() {
        Hashtable<String, TipoEquipo> tabla = new Hashtable<>();
        String sql = "SELECT * FROM ari_tipos_equipo";
        try (Statement stmt = BDConexion.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TipoEquipo tipoEquipo = new TipoEquipo();
                tipoEquipo.setCodigo(rs.getString("codigo"));
                tipoEquipo.setDescripcion(rs.getString("descripcion"));
                tabla.put(tipoEquipo.getCodigo(), tipoEquipo);
            }
        } catch (SQLException ex) {
            logger.error("Error al cargar tipos de equipo", ex);
            throw new RuntimeException(ex);
        }
        return tabla;
    }

    private Hashtable<String, Ubicacion> cargarUbicaciones() {
        Hashtable<String, Ubicacion> tabla = new Hashtable<>();
        String sql = "SELECT * FROM ari_ubicaciones";
        try (Statement stmt = BDConexion.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Ubicacion ubicacion = new Ubicacion();
                ubicacion.setCodigo(rs.getString("codigo"));
                ubicacion.setDescripcion(rs.getString("descripcion"));
                tabla.put(ubicacion.getCodigo(), ubicacion);
            }
        } catch (SQLException ex) {
            logger.error("Error al cargar tipos de equipo", ex);
            throw new RuntimeException(ex);
        }
        return tabla;
    }

    @Override
public Hashtable<String, TipoCable> cargarTiposCable() {
    Hashtable<String, TipoCable> tablaTC = new Hashtable<>();
    String sql = "SELECT * FROM ari_tipos_cable";
    try (Statement stmt = BDConexion.getConnection().createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            TipoCable tipoCable = new TipoCable();
            tipoCable.setCodigo(rs.getString("codigo"));
            tipoCable.setDescripcion(rs.getString("descripcion"));
            tipoCable.setVelocidad(rs.getInt("velocidad"));
            tablaTC.put(tipoCable.getCodigo(), tipoCable);
        }
    } catch (SQLException ex) {
        logger.error("Error al cargar tipos de cable", ex);
        throw new RuntimeException(ex);
    }
    return tablaTC;
}

}