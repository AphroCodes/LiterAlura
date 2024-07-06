package com.alura.literalura.repository;

import com.alura.literalura.model.Idiomas;
import com.alura.literalura.model.Livros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioLivros extends JpaRepository<Livros, Long> {
    Livros findByTitulo(String titulo);

    @Query("SELECT b FROM Livros b WHERE %:idioma% MEMBER OF b.idiomas")
    List<Livros> findLivrosByIdiomas(@Param("idioma") Idiomas idioma);
}
