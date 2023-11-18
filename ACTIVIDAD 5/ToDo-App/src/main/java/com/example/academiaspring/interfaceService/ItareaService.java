package com.example.academiaspring.interfaceService;

import com.example.academiaspring.modelo.Tarea;
import java.util.List;
import java.util.Optional;


public interface ItareaService {
    
        public List<Tarea>listar();
        
        public Optional<Tarea>listarId(int id);
        
        public boolean cambiarEstado(int id);
        
        public int save(Tarea t);
        
        
                
        public void delete(int id);

        
    
}
