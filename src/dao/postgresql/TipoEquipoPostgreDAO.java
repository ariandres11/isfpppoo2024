package dao.postgresql;

import conexion.BDConexion;
import dao.TipoEquipoDAO;
import modelo.TipoEquipo;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

public class TipoEquipoPostgreDAO implements TipoEquipoDAO {
    private static final Logger logger = Logger.getLogger(TipoEquipoPostgreDAO.class);

    @Override
    public void insertar(TipoEquipo tipoEquipo) {
        String sql = "INSERT INTO ari_tipos_equipo (codigo, descripcion) VALUES (?, ?)";
        try (PreparedStatement ps = BDConexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, tipoEquipo.getCodigo());
            ps.setString(2, tipoEquipo.getDescripcion());
            ps.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error al insertar tipo de equipo", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void actualizar(TipoEquipo tipoEquipo) {
        String sql = "UPDATE ari_tipos_equipo SET descripcion = ? WHERE codigo = ?";
        try (PreparedStatement ps = BDConexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, tipoEquipo.getDescripcion());
            ps.setString(2, tipoEquipo.getCodigo());
            ps.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error al actualizar tipo de equipo", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void borrar(TipoEquipo tipoEquipo) {
        String sql = "DELETE FROM ari_tipos_equipo WHERE codigo = ?";
        try (PreparedStatement ps = BDConexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, tipoEquipo.getCodigo());
            ps.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error al borrar tipo de equipo", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<TipoEquipo> buscarTodos() {
        List<TipoEquipo> listaTiposEquipo = new ArrayList<>();
        String sql = "SELECT * FROM ari_tipos_equipo";
        try (Statement stmt = BDConexion.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TipoEquipo tipoEquipo = new TipoEquipo();
                tipoEquipo.setCodigo(rs.getString("codigo"));
                tipoEquipo.setDescripcion(rs.getString("descripcion"));
                listaTiposEquipo.add(tipoEquipo);
            }
        } catch (SQLException ex) {
            logger.error("Error al listar tipos de equipo", ex);
            throw new RuntimeException(ex);
        }
        return listaTiposEquipo;
    }
}