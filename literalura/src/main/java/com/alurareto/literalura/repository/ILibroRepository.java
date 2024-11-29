package com.alurareto.literalura.repository;

import com.alurareto.literalura.modelo.Idioma;
import com.alurareto.literalura.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ILibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByIdioma(Idioma idioma);
    List<Libro> findTop10ByOrderByNumeroDeDescargasDesc();
}
