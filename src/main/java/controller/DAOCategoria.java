package controller;

import database.HibernateUtil;
import model.Categoria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DAOCategoria {

    private final SessionFactory sessionFactory;

    public DAOCategoria(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    //CONSULTAS

    //Crear
    public void guardarCategoria(Categoria categoria){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.persist(categoria);
            transaction.commit();
            System.out.println("Categoria guardada exitosamente.");
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            System.err.println("Error al guardar la categoria: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Obtener
    public Categoria obtenerCategoriaPorId(Integer id){
        Transaction transaction = null;
        Categoria categoria = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            categoria = session.get(Categoria.class, id);
            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            System.err.println("Error al obtener la categoria por id: " + e.getMessage());
            e.printStackTrace();
        }
        return categoria;
    }

    public Categoria obtenerCategoriaPorNombre(String nombreCategoria){
        Transaction transaction = null;
        Categoria categoria = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            Query<Categoria> query = session.createQuery("FROM Categoria C WHERE C.nombreCategoria = :nombre", Categoria.class);
            categoria = query.uniqueResult();
            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            System.err.println("Error al obtener la categoria por nombre: " + e.getMessage());
            e.printStackTrace();
        }
        return categoria;
    }

    public List<Categoria> obtenerTodasCategorias(){
        Transaction transaction = null;
        List<Categoria> categorias = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            Query<Categoria> query = session.createQuery("FROM Categoria", Categoria.class);
            categorias = query.list();
            transaction.commit();
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            System.err.println("Error al obtener todas las categorias: " + e.getMessage());
            e.printStackTrace();
        }
        return categorias;
    }

    //Actualizar
    public void actualizarCategoria(Categoria categoria){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.merge(categoria);
            transaction.commit();
            System.out.println("Categoria actualizada exitosamente.");
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            System.err.println("Error al actualizar la categoria: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Eliminar
    public void eliminarCategoria(Categoria categoria){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.remove(categoria);
            transaction.commit();
            System.out.println("Categoria eliminada exitosamente.");
        } catch (Exception e){
            if(transaction != null) transaction.rollback();
            System.err.println("Error al eliminar la categoria: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
