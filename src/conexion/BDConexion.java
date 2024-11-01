package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;


public class BDConexion {
    private static Connection con = null;

    /**
     * Retorna una conexión a la base de datos. Si ya existe una conexión, la
     * devuelve, de lo contrario la crea y la devuelve.
     *
     * El driver, la url, el usuario y la contraseña se leen desde el archivo
     * "jdbc.properties".
     *
     * @return La conexión a la base de datos.
     */
    public static Connection getConnection() {
        try {
            if (con == null) {
                // Con esto se determina cuando finaliza el programa
                Runtime.getRuntime().addShutdownHook(new MiShDwnHook());
                ResourceBundle rb = ResourceBundle.getBundle("jdbc");
                String driver = rb.getString("driver");
                String url = rb.getString("url");
                String usr = rb.getString("usr");
                String pwd = rb.getString("pwd");
                Class.forName(driver);
                con = DriverManager.getConnection(url, usr, pwd);

                // Establecer el esquema aquí
                try (Statement stmt = con.createStatement()) {
                    stmt.execute("SET SCHEMA 'poo2024'");
                }
            }
            return con;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error al crear la conexion", ex);
        }
    }



    public static class MiShDwnHook extends Thread {

        /**
         * Es el método que invoca la JVM justo antes de finalizar el programa.
         * En él podremos cerrar la conexión con la base de datos.
         * */
        public void run() {
            try {
                Connection con = BDConexion.getConnection();
                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }
}
