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
@Table(name = "tarea")
public class Tarea implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String titulo;
    @Column
    private String descripcion;
    @Column
    private String estado;

    private Usuario usuario;
    private Categoria categoria;

    public Tarea(String titulo, String descripcion, String estado, Usuario usuario, Categoria categoria) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.usuario = usuario;
        this.categoria = categoria;
    }
}
