package com.alurareto.literalura.repository;

import com.alurareto.literalura.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IAutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.libros")
    List<Autor> findAllAutoresWithLibros(); //    List<Author> findAllAuthorsWithBooks();

    Optional<Autor> findAutorByNombre(String nombre);

    @Query("SELECT  a FROM Autor a WHERE a.fechaNacimiento <= :año AND (a.fechaFallecimiento > :año OR a.fechaFallecimiento IS NULL)")
    List<Autor> findAutoresWitdEdad(@Param("año") int año);

    @Query("SELECT a FROM Autor a WHERE a.nombre ILIKE %:nombreAutor%")
    Optional<Autor> findAutores(String nombreAutor);
}
