package com.app.todoapp.controler;

import org.springframework.ui.Model;
import com.app.todoapp.modelo.Tarea;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.app.todoapp.interfaceService.ItareaService;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class Controlador {
    
    @Autowired
    private ItareaService service;
    
    @GetMapping("/")
    public String listar(Model model,@ModelAttribute("message") String message){
        List<Tarea>tareas=service.listar();
        model.addAttribute("tareas", tareas);
        model.addAttribute("message", message);
        return "index";
    }
    
    
    
    
    @GetMapping("/new")
    public String agregar(Model model){
        model.addAttribute("tarea", new Tarea());
        return "AGREGAR";
    }
    
   @PostMapping("/save")
    public String save(@Valid Tarea t, Model model,RedirectAttributes redirectAttributes){
        service.save(t);
        redirectAttributes.addFlashAttribute("message", "Save Success");
        return "redirect:/";
    }
 
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id,Model model,RedirectAttributes redirectAttributes){
        Optional<Tarea> tarea =service.listarId(id);
        model.addAttribute("tarea",tarea);
        
        return "EDITAR";
    }
    
    @PostMapping("/saveEdit")
    public String saveEdit(@Valid Tarea t, Model model,RedirectAttributes redirectAttributes){
        service.save(t);
        redirectAttributes.addFlashAttribute("message", "Change Success");
        return "redirect:/";
    }
    
    
    
    
    @GetMapping("/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable int id, RedirectAttributes redirectAttributes) {
        if (service.cambiarEstado(id)) {
            redirectAttributes.addFlashAttribute("message", "Estado cambiado exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("message", "No se pudo cambiar el estado");
        }
        return "redirect:/";
    }
  
    
    @GetMapping("/eliminar/{id}")
    public String delete(Model model,@PathVariable int id,RedirectAttributes redirectAttributes){
      service.delete(id);
      redirectAttributes.addFlashAttribute("message", "Delete Success");
      return "redirect:/";
    }
 
}


