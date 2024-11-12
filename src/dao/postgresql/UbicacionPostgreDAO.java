package dao.postgresql;

import conexion.BDConexion;
import dao.UbicacionDAO;
import modelo.Ubicacion;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class UbicacionPostgreDAO implements UbicacionDAO {
    private static final Logger logger = Logger.getLogger(UbicacionPostgreDAO.class);

    @Override
    public void insertar(Ubicacion ubicacion) {
        String sql = "INSERT INTO ari_ubicaciones (codigo, descripcion) VALUES (?, ?)";
        try (PreparedStatement ps = BDConexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, ubicacion.getCodigo());
            ps.setString(2, ubicacion.getDescripcion());
            ps.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error al insertar ubicación", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void actualizar(Ubicacion ubicacion) {
        String sql = "UPDATE ari_ubicaciones SET descripcion = ? WHERE codigo = ?";
        try (PreparedStatement ps = BDConexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, ubicacion.getDescripcion());
            ps.setString(2, ubicacion.getCodigo());
            ps.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error al actualizar ubicación", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void borrar(Ubicacion ubicacion) {
        String sql = "DELETE FROM ari_ubicaciones WHERE codigo = ?";
        try (PreparedStatement ps = BDConexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, ubicacion.getCodigo());
            ps.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error al borrar ubicación", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Ubicacion> buscarTodos() {
        List<Ubicacion> listaUbicaciones = new ArrayList<>();
        String sql = "SELECT * FROM ari_ubicaciones";
        try (Statement stmt = BDConexion.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Ubicacion ubicacion = new Ubicacion();
                ubicacion.setCodigo(rs.getString("codigo"));
                ubicacion.setDescripcion(rs.getString("descripcion"));
                listaUbicaciones.add(ubicacion);
            }
        } catch (SQLException ex) {
            logger.error("Error al listar ubicaciones", ex);
            throw new RuntimeException(ex);
        }
        return listaUbicaciones;
    }
}