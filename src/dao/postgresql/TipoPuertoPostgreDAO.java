package dao.postgresql;

import conexion.BDConexion;
import dao.TipoPuertoDAO;
import modelo.TipoPuerto;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

public class TipoPuertoPostgreDAO implements TipoPuertoDAO {
    private static final Logger logger = Logger.getLogger(TipoPuertoPostgreDAO.class);

    @Override
    public void insertar(TipoPuerto tipoPuerto) {
        String sql = "INSERT INTO ari_tipos_puerto (codigo, descripcion, velocidad) VALUES (?, ?, ?)";
        try (PreparedStatement ps = BDConexion.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, tipoPuerto.getCodigo());
            ps.setString(2, tipoPuerto.getDescripcion());
            ps.setInt(3, tipoPuerto.getVelocidad());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    tipoPuerto.setCodigo(rs.getString(1));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error al insertar tipo de puerto", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void borrar(TipoPuerto tipoPuerto) {
        String sql = "DELETE FROM ari_tipos_puerto WHERE codigo = ?";
        try (PreparedStatement ps = BDConexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, tipoPuerto.getCodigo());
            ps.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error al borrar tipo de puerto", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<TipoPuerto> buscarTodos() {
        List<TipoPuerto> listaTiposPuerto = new ArrayList<>();
        String sql = "SELECT * FROM ari_tipos_puerto";
        try (Statement stmt = BDConexion.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TipoPuerto tipoPuerto = new TipoPuerto();
                tipoPuerto.setCodigo(rs.getString("codigo"));
                tipoPuerto.setDescripcion(rs.getString("descripcion"));
                tipoPuerto.setVelocidad(rs.getInt("velocidad"));
                listaTiposPuerto.add(tipoPuerto);
            }
        } catch (SQLException ex) {
            logger.error("Error al listar tipos de puerto", ex);
            throw new RuntimeException(ex);
        }
        return listaTiposPuerto;
    }
}