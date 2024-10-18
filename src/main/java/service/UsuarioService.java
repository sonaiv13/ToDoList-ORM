package service;

import controller.DAOUsuario;
import model.Usuario;

public class UsuarioService {

    public DAOUsuario daoUsuario = new DAOUsuario();

    public void registrarUsuario(String nombreUsuario, String contrasenia){
        Usuario existente = daoUsuario.obtenerUsuarioPorNombre(nombreUsuario);
        if(existente != null){
            System.out.println("El nombre de usuario ya está en uso.");
            return;
        }
        Usuario usuario = new Usuario(nombreUsuario, contrasenia);
        daoUsuario.guardarUsuario(usuario);
        System.out.println("Usuario registrado exitosamente");
    }

    public Usuario autenticarUsuario(String nombreUsuario, String contrasenia){
        Usuario usuario = daoUsuario.obtenerUsuarioPorNombre(nombreUsuario);
        if(usuario != null && usuario.getContrasenia().equals(contrasenia)){
            System.out.println("Autenticación exitosa.");
            return usuario;
        }
        System.out.println("Credenciales inválidas.");
        return null;
    }


}
