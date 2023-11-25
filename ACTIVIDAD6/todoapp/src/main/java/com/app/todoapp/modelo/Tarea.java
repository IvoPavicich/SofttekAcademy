
package com.app.todoapp.modelo;

import jakarta.persistence.*;

@Entity //Establacemos que esta clase, es la entidad principal
@Table(name = "tarea") //Nos va a crear la tabla con el atributo tarea
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Con esto hacemos referencia que esto es el id de la tabla persona

    //Establacemos los atributos que va a manejar nuestra app
    private int id;

    private String description;

    private String state;

    public Tarea() {
    }

    public Tarea(int id, String description, String state) {
        this.id = id;
        this.description = description;
        this.state = state;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
