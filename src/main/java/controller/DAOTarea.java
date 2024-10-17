package controller;

import database.HibernateUtil;
import model.Tarea;
import model.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DAOTarea {

    private final SessionFactory sessionFactory;

    public DAOTarea(){
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    //CONSULTAS

    //Crear
    public void guardarTarea(Tarea tarea){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(tarea);
            transaction.commit();
            System.out.println("Tarea guardada exitosamente.");
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            System.err.println("Error al guardar la tarea: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Obtener
    public Tarea obtenerTareaPorId(Integer id){
        Transaction transaction = null;
        Tarea tarea = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            tarea = session.get(Tarea.class, id);
            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            System.err.println("Error al obtener la tarea por Id: " + e.getMessage());
            e.printStackTrace();
        }
        return tarea;
    }

    public List<Tarea> obtenerTodasTareas(){
        Transaction transaction = null;
        List<Tarea> tareas = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<Tarea> query = session.createQuery("FROM Tarea", Tarea.class);
            tareas = query.list();
            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            System.err.println("Error al obtener todas las tareas: " + e.getMessage());
            e.printStackTrace();
        }
        return tareas;
    }

    public List<Tarea> obtenerTareasPorUsuario(Integer usuarioId){
        Transaction transaction = null;
        List<Tarea> tareas = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<Tarea> query = session.createQuery("FROM Tarea T WHERE T.usuario.id = :usuarioId", Tarea.class);
            query.setParameter("usuarioId", usuarioId);
            tareas = query.list();
            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            System.err.println("Error al obtener todas las tareas por usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return tareas;
    }

    public List<Tarea> obtenerTareasPorCategorias(Integer categoriaId){
        Transaction transaction = null;
        List<Tarea> tareas = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<Tarea> query = session.createQuery("FROM Tarea T WHERE T.categoria.id = :categoriaId", Tarea.class);
            query.setParameter("categoriaId", categoriaId);
            tareas = query.list();
            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            System.err.println("Error al obtener todas las tareas por categoria: " + e.getMessage());
            e.printStackTrace();
        }
        return tareas;
    }

    //Actualizar
    public void actualizarTarea(Tarea tarea){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(tarea);
            transaction.commit();
            System.out.println("Tarea actualizada exitosamente.");
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            System.err.println("Error al actualizar la tarea: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Eliminar
    public void eliminarTarea(Tarea tarea){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(tarea);
            transaction.commit();
            System.out.println("Tarea eliminada exitosamente.");
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            System.err.println("Error al eliminar la tarea: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
