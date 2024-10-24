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
@Table(name = "categoria")
public class Categoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nombre_categoria")
    private String nombreCategoria;

    @OneToMany(mappedBy = "categoria",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Tarea> tareas = new HashSet<>();

    //Constructor
    public Categoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    //Métodos de ayuda
    public void addTarea(Tarea tarea){
        tareas.add(tarea);
        tarea.setCategoria(this);
    }

    public void removeTarea(Tarea tarea){
        tareas.add(tarea);
        tarea.setCategoria(null);
    }

}
