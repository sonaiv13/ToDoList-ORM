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

    @Enumerated(EnumType.STRING)
    @Column
    private EstadoTarea estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    //Constructor
    public Tarea(String titulo, String descripcion, EstadoTarea estado, Usuario usuario, Categoria categoria) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.usuario = usuario;
        this.categoria = categoria;
    }
}
