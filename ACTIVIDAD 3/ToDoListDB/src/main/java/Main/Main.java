
package Main;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    // Atributos de conexión
    private Connection conectar = null;
    private final String usuario = "root";
    private final String contrasena = "12345";
    private final String db = "tareas";
    private final String ip = "localhost";
    private final String puerto = "3306";
    private final Statement st = null;
    private final ResultSet rs = null;
    private final String cadena = "jdbc:mysql://" + ip + ":" + puerto + "/" + db;

    public Connection establecerConeccion() {
        try {
            conectar = DriverManager.getConnection(cadena, usuario, contrasena);
            //JOptionPane.showMessageDialog(null, "Se conectó correctamente");
        } catch (SQLException e) {
           // JOptionPane.showMessageDialog(null, "No se pudo conectar" + e.toString());
        }
        return conectar;
    }

    public static void main(String[] args) {
        Main app = new Main(); // Crear una instancia de la clase Main
        app.establecerConeccion(); // Llamar al método para establecer la conexión
        // Puedes realizar más operaciones con la conexión aquí
        
        java.awt.EventQueue.invokeLater(() -> {
            new ToDoList().setVisible(true);
        });
    }
}
