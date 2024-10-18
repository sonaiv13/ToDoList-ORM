package service;

import controller.DAOCategoria;
import model.Categoria;

import java.util.List;

public class CategoriaService {

    private DAOCategoria daoCategoria = new DAOCategoria();

    public void crearCategoria(String nombre){
        Categoria existente = daoCategoria.obtenerCategoriaPorNombre(nombre);
        if(existente != null){
            System.out.println("La categoria ya existe.");
            return;
        }
        Categoria categoria = new Categoria(nombre);
        daoCategoria.guardarCategoria(categoria);
        System.out.println("Categoria creada exitosamente.");
    }

    public List<Categoria> listarCategorias(){
        return daoCategoria.obtenerTodasCategorias();
    }

    public Categoria obtenerCategoriaPorId(Integer categoriaId){
        return  daoCategoria.obtenerCategoriaPorId(categoriaId);
    }

}
