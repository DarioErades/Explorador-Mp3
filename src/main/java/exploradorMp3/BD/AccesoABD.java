package exploradorMp3.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccesoABD {
    public static Connection obtenerConexionPgSql(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e); // No se ha encontrado esa clase
        }

        String url = "jdbc:postgresql://localhost:5432/exploradormp3";
        String usuario = "postgres";; // Usuario default
        String password = "1234";

        try {
            return DriverManager.getConnection(url,usuario,password);
        } catch (SQLException e) {
            throw new RuntimeException(e); // Error conectando a la base de datos
        }
    }
}
