package dao.postgresql;

import conexion.BDConexion;
import dao.TipoCableDAO;
import modelo.TipoCable;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class TipoCablePostgreDAO implements TipoCableDAO {
    private static final Logger logger = Logger.getLogger(TipoCablePostgreDAO.class);

    @Override
    public void insertar(TipoCable tipoCable) {
        String sql = "INSERT INTO ari_tipos_cable (codigo, descripcion, velocidad) VALUES (?, ?, ?)";
        try (PreparedStatement ps = BDConexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, tipoCable.getCodigo());
            ps.setString(2, tipoCable.getDescripcion());
            ps.setInt(3, tipoCable.getVelocidad());
            ps.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error al insertar tipo de cable", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void borrar(TipoCable tipoCable) {
        String sql = "DELETE FROM ari_tipos_cable WHERE codigo = ?";
        try (PreparedStatement ps = BDConexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, tipoCable.getCodigo());
            ps.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error al borrar tipo de cable", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<TipoCable> buscarTodos() {
        List<TipoCable> listaTiposCable = new ArrayList<>();
        String sql = "SELECT * FROM ari_tipos_cable";
        try (Statement stmt = BDConexion.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TipoCable tipoCable = new TipoCable();
                tipoCable.setCodigo(rs.getString("codigo"));
                tipoCable.setDescripcion(rs.getString("descripcion"));
                tipoCable.setVelocidad(rs.getInt("velocidad"));
                listaTiposCable.add(tipoCable);
            }
        } catch (SQLException ex) {
            logger.error("Error al listar tipos de cable", ex);
            throw new RuntimeException(ex);
        }
        return listaTiposCable;
    }
}