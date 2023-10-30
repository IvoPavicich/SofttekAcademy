
package com.mycompany.todolist;
public class Main {

    
    public static void main(String args[]) {
      
        java.awt.EventQueue.invokeLater(() -> {
            new ToDoListApp().setVisible(true);
        });
    }
    
}
