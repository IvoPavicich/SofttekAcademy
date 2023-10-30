package com.mycompany.todolist;

public class Task {
    private int id;
    private String description;
    private String status;
    private static int taskId = 0;
    
    public Task() {
       
    }

    public Task(String description, String status) {
       this.id = generarNuevoId();
       this.description = description;
       this.status = status;
    }
   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
  

    /*public static void incrementarTaskIdCounter() {
        taskId++;
    }*/
    public static int generarNuevoId() {
        return ++taskId;
    }
    
    public Object[] toArray(){
        Object[] obj = new Object[3];
        
        obj[0] = id;
        obj[1] = description;
        obj[2] = status;
        
        return obj;
    }
    
    
}
