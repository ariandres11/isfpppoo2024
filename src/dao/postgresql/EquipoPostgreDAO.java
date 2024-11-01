package dao.postgresql;

import conexion.BDConexion;
import dao.EquipoDAO;
import modelo.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class EquipoPostgreDAO implements EquipoDAO {
    private static final Logger logger = Logger.getLogger(EquipoPostgreDAO.class);
    private Hashtable<String, TipoPuerto> tablaTiposPuerto;
    private Hashtable<String, TipoEquipo> tablaTiposEquipo;
    private Hashtable<String, Ubicacion> tablaUbicaciones;

    public EquipoPostgreDAO() {
        tablaTiposPuerto = cargarTiposPuerto();
        tablaTiposEquipo = cargarTiposEquipo();
        tablaUbicaciones = cargarUbicaciones();
    }

    @Override
    public void insertar(Equipo equipo) {
        String sqlEquipo = "INSERT INTO ari_equipos (codigo, descripcion, marca, modelo, ubicacion, tipo_equipo, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement psEquipo = BDConexion.getConnection().prepareStatement(sqlEquipo)) {
            psEquipo.setString(1, equipo.getCodigo());
            psEquipo.setString(2, equipo.getDescripcion());
            psEquipo.setString(3, equipo.getMarca());
            psEquipo.setString(4, equipo.getModelo());
            psEquipo.setString(5, equipo.getUbicacion().getCodigo());

            // Verificar si el tipo de equipo es nulo
            if (equipo.getTipoEquipo() != null) {
                psEquipo.setString(6, equipo.getTipoEquipo().getCodigo());
            } else {
                // Manejar el caso en que el tipo de equipo es nulo
                psEquipo.setString(6, null); // o algún valor por defecto
            }

            psEquipo.setBoolean(7, equipo.isEstado());
            psEquipo.executeUpdate();
            insertarIps(equipo);
            insertarPuertos(equipo);
        } catch (SQLException ex) {
            logger.error("Error al insertar equipo", ex);
            throw new RuntimeException(ex);
        }
    }


            @Override
    public void actualizar(Equipo equipo) {
        String sqlEquipo = "UPDATE ari_equipos SET descripcion = ?, marca = ?, modelo = ?, ubicacion = ?, tipo_equipo = ?, estado = ? WHERE codigo = ?";
        try (PreparedStatement psEquipo = BDConexion.getConnection().prepareStatement(sqlEquipo)) {
            psEquipo.setString(1, equipo.getDescripcion());
            psEquipo.setString(2, equipo.getMarca());
            psEquipo.setString(3, equipo.getModelo());
            psEquipo.setString(4, equipo.getUbicacion().getCodigo());
            psEquipo.setString(5, equipo.getTipoEquipo().getCodigo());
            psEquipo.setBoolean(6, equipo.isEstado());
            psEquipo.setString(7, equipo.getCodigo());
            psEquipo.executeUpdate();
            borrarIps(equipo.getCodigo());
            insertarIps(equipo);
            borrarPuertos(equipo.getCodigo());
            insertarPuertos(equipo);
        } catch (SQLException ex) {
            logger.error("Error al actualizar equipo", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void borrar(Equipo equipo) {
        String sqlEquipo = "DELETE FROM ari_equipos WHERE codigo = ?";
        try (PreparedStatement psEquipo = BDConexion.getConnection().prepareStatement(sqlEquipo)) {
            psEquipo.setString(1, equipo.getCodigo());
            psEquipo.executeUpdate();
            borrarIps(equipo.getCodigo());
            borrarPuertos(equipo.getCodigo());
        } catch (SQLException ex) {
            logger.error("Error al borrar equipo", ex);
            throw new RuntimeException(ex);
        }
    }

    public void borrarPuertos(String codigoEquipo) {
        String sqlPuerto = "DELETE FROM ari_equipos_puertos WHERE equipo_codigo = ?";
        try (PreparedStatement psPuerto = BDConexion.getConnection().prepareStatement(sqlPuerto)) {
            psPuerto.setString(1, codigoEquipo);
            psPuerto.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error al borrar puertos de equipo", ex);
            throw new RuntimeException(ex);
        }
    }

    public void borrarIps(String codigoEquipo) {
        String sqlIp = "DELETE FROM ari_ips WHERE equipo = ?";
        try (PreparedStatement psIp = BDConexion.getConnection().prepareStatement(sqlIp)) {
            psIp.setString(1, codigoEquipo);
            psIp.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error al borrar ips de equipo", ex);
            throw new RuntimeException(ex);
        }
    }

        @Override
        public List<Equipo> buscarTodos() {
        String sql = "SELECT * FROM ari_equipos";
            try (Statement stmt = BDConexion.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
                List<Equipo> listaEquipos = new ArrayList<>();
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

                listaEquipos.add(equipo);
            }
                return listaEquipos;
            }    catch (SQLException ex) {
                    logger.error("Error al cargar equipos", ex);
                    throw new RuntimeException(ex);
                }
         }
            private void cargarIps (Equipo equipo) {
                String sqlIp = "SELECT ip FROM ari_ips WHERE equipo = ?";
                try (PreparedStatement psIp = BDConexion.getConnection().prepareStatement(sqlIp)) {
                    psIp.setString(1, equipo.getCodigo());
                    try (ResultSet rs = psIp.executeQuery()) {
                        while (rs.next()) {
                            equipo.agregarDireccionIP(rs.getString("ip"));
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            private void cargarPuertos (Equipo equipo) throws SQLException {
                String sqlPuerto = "SELECT tipo_puerto_codigo, cantidad_total FROM ari_equipos_puertos WHERE equipo_codigo = ?";
                try (PreparedStatement psPuerto = BDConexion.getConnection().prepareStatement(sqlPuerto)) {
                    psPuerto.setString(1, equipo.getCodigo());
                    try (ResultSet rs = psPuerto.executeQuery()) {
                        while (rs.next()) {
                            String tipoPuertoCodigo = rs.getString("tipo_puerto_codigo");
                            int cantidad = rs.getInt("cantidad_total");
                            TipoPuerto tipoPuerto = tablaTiposPuerto.get(tipoPuertoCodigo);
                            if (tipoPuerto != null) {
                                equipo.agregarPuerto(cantidad, tipoPuerto);
                            }
                        }
                    }
                }
            }

            @Override
            public Hashtable<String, TipoPuerto> cargarTiposPuerto () {
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
            public Hashtable<String, TipoEquipo> cargarTiposEquipo () {
                Hashtable<String, TipoEquipo> tabla = new Hashtable<>();
                String sql = "SELECT * FROM ari_tipos_equipo";
                try (Statement stmt = BDConexion.getConnection().createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        TipoEquipo tipoEquipo = new TipoEquipo();
                        tipoEquipo.setCodigo(rs.getString("codigo"));
                        tipoEquipo.setDescripcion(rs.getString("descripcion"));
                        tabla.put(rs.getString("codigo"), tipoEquipo);
                    }
                } catch (SQLException e) {
                    logger.error("Error al cargar tipos de equipo", e);
                }
                return tabla;
            }

            @Override
            public Hashtable<String, Ubicacion> cargarUbicaciones () {
                Hashtable<String, Ubicacion> tabla = new Hashtable<>();
                String sql = "SELECT * FROM ari_ubicaciones";
                try (Statement stmt = BDConexion.getConnection().createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        Ubicacion ubicacion = new Ubicacion();
                        ubicacion.setCodigo(rs.getString("codigo"));
                        ubicacion.setDescripcion(rs.getString("descripcion"));
                        tabla.put(rs.getString("codigo"), ubicacion);
                    }
                } catch (SQLException e) {
                    logger.error("Error al cargar ubicaciones", e);
                }
                return tabla;
            }

            private void insertarIps (Equipo equipo) throws SQLException {
                String sqlIp = "INSERT INTO ari_ips (ip, equipo) VALUES (?, ?)";
                for (String ip : equipo.getDireccionesIP()) {
                    try (PreparedStatement psIp = BDConexion.getConnection().prepareStatement(sqlIp)) {
                        psIp.setString(1, ip);
                        psIp.setString(2, equipo.getCodigo());
                        psIp.executeUpdate();
                    }
                }
            }

            private void insertarPuertos (Equipo equipo) throws SQLException {
                String sqlPuerto = "INSERT INTO ari_equipos_puertos (equipo_codigo, tipo_puerto_codigo, cantidad_total) VALUES (?, ?, ?)";
                for (Map.Entry<String, Integer> entry : equipo.getMapaPuertos().entrySet()) {
                    try (PreparedStatement psPuerto = BDConexion.getConnection().prepareStatement(sqlPuerto)) {
                        psPuerto.setString(1, equipo.getCodigo());
                        psPuerto.setString(2, tablaTiposPuerto.get(entry.getKey()).getCodigo());
                        psPuerto.setInt(3, entry.getValue());
                        psPuerto.executeUpdate();
                    }
                }
            }
        }
