package com.alurareto.literalura.modelo;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Integer numeroDeDescargas;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autores = new HashSet<>();

    private String formatoLibro(Libro libro) {
        return """
                ---------------------------
                Título: %s
                Autor(es): %s
                Idioma: %s
                Número de descargas: %d
                ---------------------------
                """.formatted(
                libro.getTitulo(),
                libro.getAutores().stream().map(Autor::getNombre).collect(Collectors.joining(", ")),
                libro.getIdioma(),
                libro.getNumeroDeDescargas()
        );
    }

    public Libro(){ }

    public Libro(DatosLibro b) {
        this.titulo = b.titulo();
        this.numeroDeDescargas = b.numeroDeDescargas();
        this.autores = autores;
        if(!b.idiomas().isEmpty()) {
            this.idioma = Idioma.fromString(b.idiomas().get(0));
        } else {
            throw new IllegalArgumentException("No entrgo un idioma valido");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public int getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(int numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Set<Autor> getAutores() {
        return autores;
    }

    public void setAutores(Set<Autor> autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "Título='" + titulo + '\'' +
                ", Idioma=" + idioma +
                ", Numero de Descargas=" + numeroDeDescargas +
                ", Autores=" + autores.stream()
                .map(Autor::getNombre)
                .collect(Collectors.joining(", ")) +
                '}';
    }

    public void addAutor(Autor autor) {
        autores.add(autor);
        autor.getLibros().add(this);
    }

    public void removeAutor(Autor autor) {
        autores.remove(autor);
        autor.getLibros().remove(this);
    }
}
