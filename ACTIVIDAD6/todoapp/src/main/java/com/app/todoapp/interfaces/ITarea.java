package com.app.todoapp.interfaces;

import com.app.todoapp.modelo.Tarea;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITarea extends CrudRepository<Tarea, Integer> {

    public Optional<Tarea> findAllById(int id);
    
}
