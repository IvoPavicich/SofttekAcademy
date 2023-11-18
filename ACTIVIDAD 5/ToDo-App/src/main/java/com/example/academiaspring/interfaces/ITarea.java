package com.example.academiaspring.interfaces;

import com.example.academiaspring.modelo.Tarea;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITarea extends CrudRepository<Tarea, Integer> {

    public Optional<Tarea> findAllById(int id);
    
}
