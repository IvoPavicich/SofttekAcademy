package Dao;

import Tasks.Disenio;
import javax.swing.JOptionPane;
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
    
    private final Statement st = null;
    
    private final ResultSet  rs = null;
    
    private final String cadena = "jdbc:mysql://localhost:3306/tareas";
    
    public Connection establecerConeccion(){
        
        try{
            this.conectar = DriverManager.getConnection(cadena, usuario, contrasena);
            JOptionPane.showMessageDialog(null, "Se conectó correctamente");
        }catch (Exception e){
            System.out.println(e);
        }
        return this.conectar;
        
    }
     public static void main(String[] args) {
         Main app = new Main();
         app.establecerConeccion();
         java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
                 new Disenio().setVisible(true);
             }
         });
    }
    
}