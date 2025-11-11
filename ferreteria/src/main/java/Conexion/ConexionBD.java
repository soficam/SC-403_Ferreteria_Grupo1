package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/ferreteria";
    private static final String USER = "usuario_prueba";
    private static final String PASSWORD = "Usuar1o_Clave"; 

    private static Connection conn = null;

    public static Connection getConexion() {
        if (conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println(" Conexión establecida correctamente");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(" Error de conexión: " + e.getMessage());
            }
        }
        return conn;
    }

    public static void cerrarConexion() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println(" Conexión cerrada");
            }
        } catch (SQLException e) {
            System.out.println(" Error al cerrar conexión: " + e.getMessage());
        }
    }
}
