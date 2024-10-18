package service;

import controller.DAOTarea;
import model.Categoria;
import model.EstadoTarea;
import model.Tarea;
import model.Usuario;

import java.util.List;

public class TareaService {

    private DAOTarea daoTarea = new DAOTarea();

    public void crearTarea(String titulo, String descripcion, EstadoTarea estado, Usuario usuario, Integer categoriaId){
        CategoriaService categoriaService = new CategoriaService();
        Categoria categoria = categoriaService.obtenerCategoriaPorId(categoriaId);
        if(categoria == null){
            System.out.println("Categoria no encontrada.");
            return;
        }
        Tarea tarea = new Tarea(titulo, descripcion, estado, usuario, categoria);
        daoTarea.guardarTarea(tarea);
        System.out.println("Tarea creada exitosamente.");
    }

    public List<Tarea> listarTareasPorUsuario(Integer usuarioId){
        return daoTarea.obtenerTareasPorUsuario(usuarioId);
    }

    public void actualizarEstadoTarea(Integer tareaId, EstadoTarea nuevoEstado){
        Tarea tarea = daoTarea.obtenerTareaPorId(tareaId);
        if(tarea != null){
            tarea.setEstado(nuevoEstado);
            daoTarea.actualizarTarea(tarea);
            System.out.println("Estado de la tarea actualizado.");
        } else {
            System.out.println("Tarea no encontrada.");
        }
    }

    public void eliminarTarea(Integer tareaId){
        Tarea tarea = daoTarea.obtenerTareaPorId(tareaId);
        if(tarea != null){
            daoTarea.eliminarTarea(tarea);
            System.out.println("Tarea eliminada.");
        } else {
            System.out.println("Tarea no encontrada.");
        }
    }

}
