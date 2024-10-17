package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    @Column
    private String contrasenia;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Tarea> tareas = new HashSet<>();

    //Constructor
    public Usuario(String nombreUsuario, String contrasenia) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
    }

    //Métodos de ayuda
    public void addTarea(Tarea tarea){
        tareas.add(tarea);
        tarea.setUsuario(this);
    }

    public void removeTarea(Tarea tarea){
        tareas.remove(tarea);
        tarea.setUsuario(null);
    }

}
