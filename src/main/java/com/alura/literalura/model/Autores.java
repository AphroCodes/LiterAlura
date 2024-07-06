package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;

    @ManyToMany(mappedBy = "autores", fetch = FetchType.EAGER)
    private List<Livros> livros = new ArrayList<>();

    public Autores(DadosAutores dadosAutores) {
        this.nome = dadosAutores.nome();
        this.setAnoNascimento(dadosAutores.anoNascimento());
        this.setAnoFalecimento(dadosAutores.anoFalecimento());
        this.livros = new ArrayList<>();
    }

    public Autores() {}

    public String getNome(){
        return nome;
    }

    public void setNome(String name){
        this.nome = name;
    }

    public int getAnoNascimento(){
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento){
        if (anoNascimento == null){
            this.anoNascimento = 0;
        } else {
            this.anoNascimento = anoNascimento;
        }
    }

    public int getAnoFalecimento(){
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento){
        if (anoFalecimento == null){
            this.anoFalecimento = 0;
        } else {
            this.anoFalecimento = anoFalecimento;
        }
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public List<Livros> getLivros(){
        return livros;
    }
    public void setLivros(List<Livros> livros){
        this.livros = livros;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + "\n" +
                "Ano de Nascimento: " + anoNascimento + "\n" +
                "Ano de Falecimento: " + anoFalecimento + "\n";
    }
}
