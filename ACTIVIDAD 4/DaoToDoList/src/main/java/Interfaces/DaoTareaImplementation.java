
package Interfaces;
import Dao.Main;
import Tasks.Task;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;



public class DaoTareaImplementation implements DaoTask{

      //Establemos una estancia de la clase main aquí
    Main main = new Main();
    
    public DaoTareaImplementation() {
    }

    @Override
    public void registrar(Task task) {
                //Implementamos un try-catch
        try {

            Connection conectar = this.main.establecerConeccion();
            PreparedStatement insertar = conectar.prepareStatement("INSERT INTO tarea (ID,DESCRIPTION,STATE) VALUES (?, ?, ?)");
            insertar.setInt(1, task.getId());
            insertar.setString(2, task.getDescription());
            insertar.setString(3, task.getState());
            insertar.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    public void modificar(Task task) {
        try {

            Connection conectar = main.establecerConeccion();

            PreparedStatement modificar = conectar.prepareStatement("UPDATE tarea SET description = ?, state = ? WHERE id = ?");

            modificar.setString(1, task.getDescription());
            modificar.setString(2, task.getState());
            modificar.setInt(3, task.getId());
            modificar.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void eliminar(Task task) {
              try {

            Connection conectar = main.establecerConeccion();

            PreparedStatement eliminar = conectar.prepareStatement("delete from tarea where id = ?");

            eliminar.setInt(1, task.getId());
            eliminar.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void buscar(Task task) {
         try {

            Connection conectar = main.establecerConeccion();

            PreparedStatement buscar = conectar.prepareStatement("select * from tarea where id = ?");

            buscar.setInt(1, task.getId());

            ResultSet consulta = buscar.executeQuery();

            if (consulta.next()) {
                task.setId(Integer.parseInt(consulta.getString("ID")));
                task.setDescription(consulta.getString("DESCRIPTION"));
                task.setState(consulta.getString("STATE"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

   
          
}
