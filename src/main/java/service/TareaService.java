package service;

import controller.DAOTarea;
import database.HibernateUtil;
import model.Categoria;
import model.EstadoTarea;
import model.Tarea;
import model.Usuario;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

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
        List<Tarea> tareas = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Tarea> query = session.createQuery(
                    "SELECT t FROM Tarea t LEFT JOIN FETCH t.categoria WHERE t.usuario.id = :usuarioId",
                    Tarea.class);
            query.setParameter("usuarioId", usuarioId);
            tareas = query.getResultList();

            // Inicializar manualmente la categoría dentro de la sesión abierta
            for (Tarea tarea : tareas) {
                if (tarea.getCategoria() != null) {
                    Hibernate.initialize(tarea.getCategoria());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tareas;
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
