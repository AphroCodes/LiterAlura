package com.alura.literalura.repository;

import com.alura.literalura.model.Autores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioAutores extends JpaRepository<Autores, Long> {
    List<Autores> findByNome(String nome);

    @Query("SELECT a FROM Autores a WHERE a.anoNascimento IS NOT NULL AND (a.anoFalecimento IS NULL OR :year <= a.anoFalecimento) AND :year >= a.anoNascimento")
    List<Autores> findLivingAuthorsInYear(@Param("year") int year);

    @Query("SELECT a FROM Autores a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Autores> findByNameContainingIgnoreCase(@Param("nome") String nome);

}
