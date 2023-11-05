package Interfaces;

import Tasks.Task;

public interface DaoTask {
    
    public void registrar(Task task);

    public void modificar(Task task);

    public void eliminar(Task task);

    public void buscar(Task task);
    
    
}
