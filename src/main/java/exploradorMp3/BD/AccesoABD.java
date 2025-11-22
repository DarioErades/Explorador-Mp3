package exploradorMp3.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccesoABD {
    public static Connection obtenerConexionPgSql() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e); // No se ha encontrado esa clase
        }

        String url = "jdbc:postgresql://localhost:5432/exploradormp3";
        String usuario = "postgres";
        // Usuario default
        String password = "1234";

        try {
            return DriverManager.getConnection(url, usuario, password);
        } catch (SQLException e) {
            throw new RuntimeException(e); // Error conectando a la base de datos
        }
    }

    /*
     * Esto es necesario ya que no puedes obtener la conexión de arriba
     * sin que esté creada la base de datos
     */
    public static Connection obtenerConexionSinEstarCreada() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:postgresql://localhost:5432/postgres";
        String usuario = "postgres";
        String password = "1234";

        try {
            return DriverManager.getConnection(url, usuario, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
