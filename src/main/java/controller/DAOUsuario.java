package controller;

import database.HibernateUtil;
import model.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DAOUsuario {

    private final SessionFactory sessionFactory;

    public DAOUsuario(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    //CONSULTAS

    //Crear
    public void guardarUsuario(Usuario usuario){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(usuario);
            transaction.commit();
            System.out.println("Usuario guardado exitosamente.");
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            System.err.println("Error al guardar el usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Obtener
    public Usuario obtenerUsuarioPorId(Integer id){
        Transaction transaction = null;
        Usuario usuario = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            usuario = session.get(Usuario.class, id);
            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            System.err.println("Error al obtener el usuario por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return usuario;
    }

    public Usuario obtenerUsuarioPorNombre(String nombreUsuario){
        Transaction transaction = null;
        Usuario usuario = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<Usuario> query = session.createQuery("FROM Usuario U WHERE U.nombreUsuario = :nombre", Usuario.class);
            query.setParameter("nombre", nombreUsuario);
            usuario = query.uniqueResult();
            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            System.err.println("Error al obtenher el usuario por nombre: " + e.getMessage());
            e.printStackTrace();
        }
        return usuario;
    }

    public List<Usuario> obtenerTodosUsuarios(){
        Transaction transaction = null;
        List<Usuario> usuarios = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<Usuario> query = session.createQuery("FROM Usuario", Usuario.class);
            usuarios = query.list();
            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            System.err.println("Error al obtener todos los usuarios: " + e.getMessage());
            e.printStackTrace();
        }
        return usuarios;
    }

    //Actualizar
    public void actualizarUsuario(Usuario usuario){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(usuario);
            transaction.commit();
            System.out.println("Usuario actualizado exitosamente.");
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            System.err.println("Error al actualizar el usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Eliminar
    public void eliminarUsuario(Usuario usuario){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(usuario);
            transaction.commit();
            System.out.println("Usuario eliminado exitosamente.");
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            System.err.println("Error al eliminar el usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
