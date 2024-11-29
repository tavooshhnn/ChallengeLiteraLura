package com.alurareto.literalura.modelo;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "autores")
    private Set<Libro> libros = new HashSet<>();

    private String formatoAutor(Autor autor) {
        StringBuilder formatted = new StringBuilder("""
                ***************************
                Autor: %s
                Año de nacimiento: %s
                Año de fallecimiento: %s
                Libros escritos:
                """.formatted(
                autor.getNombre(),
                Optional.ofNullable(autor.getFechaNacimiento()).map(String::valueOf).orElse("Desconocido"),
                Optional.ofNullable(autor.getFechaFallecimiento()).map(String::valueOf).orElse("Desconocido")
        ));
        autor.getLibros().forEach(libro -> formatted.append(" - ").append(libro.getTitulo()).append("\n"));
        formatted.append("***************************");
        return formatted.toString();
    }

    public Autor(){ }

    public Autor(DatosAutor a) {
        this.nombre = a.nombre();
        this.fechaNacimiento = a.fechaNacimiento();
        this.fechaFallecimiento = a.fechaFallecimiento();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "Nombre='" + nombre + '\'' +
                ", Año de Nacimiento=" + (fechaNacimiento != null ? fechaNacimiento: "Desconocido") +
                ", Año de Fallecimiento=" + (fechaFallecimiento != null ? fechaFallecimiento : "vivo") +
                '}';
    }

    public void addBook(Libro libro) {
        libros.add(libro);
        libro.getAutores().add(this);
    }

    public void removeBook(Libro libro) {
        libros.remove(libro);
        libro.getAutores().remove(this);
    }
}
