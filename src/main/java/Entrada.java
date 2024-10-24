import database.HibernateUtil;
import model.Categoria;
import model.EstadoTarea;
import model.Tarea;
import model.Usuario;
import service.CategoriaService;
import service.TareaService;
import service.UsuarioService;

import java.util.List;
import java.util.Scanner;

public class Entrada {

    private static UsuarioService usuarioService = new UsuarioService();
    private static CategoriaService categoriaService = new CategoriaService();
    private static TareaService tareaService = new TareaService();

    private static Scanner scanner = new Scanner(System.in);
    private static Usuario usuarioActual = null;

    public static void main(String[] args) {
        mostrarMenuInicial();
        HibernateUtil.shutdown();
    }

    private static void mostrarMenuInicial() {
        while (true){
            System.out.println("=== Aplicación de Gestión de Tareas ===");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar Sesión");
            System.out.println("3. Salir");
            System.out.println("Selecciona una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion){
                case "1":
                    registrarUsuario();
                    break;
                case "2":
                    iniciarSesion();
                    break;
                case "3":
                    System.out.println("Adiós!");
                    return;
                default:
                    System.out.println("Opción inválida. Intenta de nuevo");
            }
        }
    }


    private static void registrarUsuario() {
        System.out.println("Nombre de Usuario: ");
        String nombre = scanner.nextLine();
        System.out.println("Contraseña: ");
        String contrasenia = scanner.nextLine();
        usuarioService.registrarUsuario(nombre, contrasenia);
    }

    private static void iniciarSesion() {
        System.out.println("Nombre de Usuario: ");
        String nombre = scanner.nextLine();
        System.out.println("Contraseña: ");
        String contrasenia = scanner.nextLine();
        Usuario usuario = usuarioService.autenticarUsuario(nombre, contrasenia);
        if(usuario != null){
            usuarioActual = usuario;
            mostrarMenuPrincipal();
        }
    }

    private static void mostrarMenuPrincipal() {
        while(usuarioActual != null){
            System.out.println("\n=== Menú Principal ===");
            System.out.println("1. Gestionar Tareas");
            System.out.println("2. Gestionar Categorías");
            System.out.println("3. Cerrar Sesión");
            System.out.println("Selecciona una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion){
                case "1":
                    gestionarTareas();
                    break;
                case "2":
                    gestionarCategorias();
                    break;
                case "3":
                    usuarioActual = null;
                    System.out.println("Sesión cerrada.");
                    break;
                default:
                    System.out.println("Opción inválida. Intenta de nuevo");
            }
        }
    }


    //GESTIÓN TAREAS
    private static void gestionarTareas() {
        while(true){
            System.out.println("\n=== Gestión de Tareas ===");
            System.out.println("1. Crear Tarea");
            System.out.println("2. Listar Tareas");
            System.out.println("3. Actualizar Estado");
            System.out.println("4. Eliminar Tarea");
            System.out.println("5. Volver al Menú Principal");
            System.out.println("Selecciona una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion){
                case "1":
                    crearTarea();
                    break;
                case "2":
                    listarTareas();
                    break;
                case "3":
                    actualizarEstadoTarea();
                    break;
                case "4":
                    eliminarTarea();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Opción inválida. Intenta de nuevo");
            }
        }
    }

    private static void crearTarea() {
        System.out.println("Título: ");
        String titulo = scanner.nextLine();
        System.out.println("Descripción de la Tarea: ");
        String descripcion = scanner.nextLine();
        System.out.println("Selecciona el Estado de la Tarea: ");
        for(EstadoTarea estado : EstadoTarea.values()){
            System.out.println(estado.ordinal() + 1 + ". " + estado);
        }

        System.out.println("Opción: ");
        int opcionEstado = Integer.parseInt(scanner.nextLine());
        EstadoTarea estado = EstadoTarea.values()[opcionEstado - 1];

        listarCategorias();
        System.out.println("Selecciona la Categoría por ID: ");
        Integer categoriaId = Integer.parseInt(scanner.nextLine());

        tareaService.crearTarea(titulo, descripcion, estado, usuarioActual, categoriaId);
    }

    private static void listarTareas() {
        List<Tarea> tareas = tareaService.listarTareasPorUsuario(usuarioActual.getId());
        System.out.println("\nTus Tareas:");
        for(Tarea tarea : tareas){
            System.out.println("ID: " + tarea.getId());
            System.out.println("Título: " + tarea.getTitulo());
            System.out.println("Descripción: " + tarea.getDescripcion());
            System.out.println("Estado: " + tarea.getEstado());

            if(tarea.getCategoria() != null){
                System.out.println("Categoria: " + tarea.getCategoria().getNombreCategoria());
            } else {
                System.out.println("Categoría: Sin Categoría");
            }
            System.out.println("----------------------------------");
        }
    }
    private static void actualizarEstadoTarea() {
        listarTareas();
        System.out.println("Ingresa el ID de la Tarea a actualizar: ");
        Integer tareaId = Integer.parseInt(scanner.nextLine());
        System.out.println("Selecciona el Nuevo Estado:");
        for(EstadoTarea estado : EstadoTarea.values()){
            System.out.println(estado.ordinal() + 1 + ". " + estado);
        }

        System.out.println("Opción: ");
        int opcionEstado = Integer.parseInt(scanner.nextLine());
        EstadoTarea nuevoEstado = EstadoTarea.values()[opcionEstado - 1];

        tareaService.actualizarEstadoTarea(tareaId, nuevoEstado);
    }

    private static void eliminarTarea() {
        listarTareas();
        System.out.println("Ingresa el ID de la Tarea a eliminar: ");
        Integer tareaId = Integer.parseInt(scanner.nextLine());
        tareaService.eliminarTarea(tareaId);
    }

    //GESTIÓN CATEGORÍAS
    private static void gestionarCategorias() {
        while(true){
            System.out.println("\n=== Gestión de Categroias ===");
            System.out.println("1. Crear Categoría");
            System.out.println("2. Listar Categorías");
            System.out.println("3. Volver al Menú Principal");
            System.out.println("Selecciona una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion){
                case "1":
                    crearCategoria();
                    break;
                case "2":
                    listarCategorias();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Opción inválida. Intenta de nuevo");
            }
        }
    }

    private static void crearCategoria() {
        System.out.println("Nombre de la categoría: ");
        String nombre = scanner.nextLine();
        categoriaService.crearCategoria(nombre);
    }

    private static void listarCategorias() {
        List<Categoria> categorias = categoriaService.listarCategorias();
        System.out.println("\nCategorías disponibles:");
        for(Categoria categoria : categorias){
            System.out.println(categoria.getId() + ". " + categoria.getNombreCategoria());
        }
    }

}
