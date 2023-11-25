package com.app.todoapp.service;


import com.app.todoapp.interfaceService.ItareaService;
import com.app.todoapp.interfaces.ITarea;
import com.app.todoapp.modelo.Tarea;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



//import java.util.Optional;
@Service
public class TareaService implements ItareaService{
    
    @Autowired
    private ITarea data;

    @Override
    public List<Tarea> listar() {
        return(List<Tarea>) data.findAll();
    }

    @Override
    public Optional<Tarea> listarId(int id) {
        return data.findAllById(id);
    }
 
/**********************************************/    
    @Override
    public int save(Tarea t) {
        int res=0;
        Tarea tarea = data.save(t);
        if(!tarea.equals(null)){
            res=1;
        }
      return res;
    }
    
   
    @Override
    public void delete(int id) {
        data.deleteById(id);
    }
 /*****************************************************************/   
   @Override
    public boolean cambiarEstado(int id) {
        Optional<Tarea> tareaOpt = data.findById(id);
        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            // Cambiar lógica de estado según tus necesidades
            tarea.setState("Completada");  // Cambia "NuevoEstado" según tus necesidades
            data.save(tarea);
            return true;
        }
        return false;
    }
}
    
  