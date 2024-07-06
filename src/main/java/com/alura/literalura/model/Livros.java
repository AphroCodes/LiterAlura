package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "livro")
public class Livros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "autor_livro",
            joinColumns = @JoinColumn(name = "id_livro"),
            inverseJoinColumns = @JoinColumn(name = "id_autor")
    )
    private List<Autores> autores;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "idioma_livro", joinColumns = @JoinColumn(name = "id_livro"))
    @Enumerated(EnumType.STRING)
    @Column(name = "idiomas")
    private List<Idiomas> idiomas;
    private Integer contadorDownloads;

    public Livros(DadosLivros dadosLivros) {
        this.titulo = dadosLivros.titulo();
        this.idiomas = dadosLivros.idiomas();
        this.contadorDownloads = dadosLivros.contadorDownloads();
    }

    public Livros(){

    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public List<Autores> getAutores(){
        return autores;
    }

    public Integer getContadorDownloads(){
        return contadorDownloads;
    }

    public void setContadorDownloads(Integer contadorDownloads){
        this.contadorDownloads = contadorDownloads;
    }

    public void addAutores(Autores autores){
        this.autores.add(autores);
        if (!autores.getLivros().contains(this)) {
            autores.getLivros().add(this);
        }
    }

    public void setAutores(List<Autores> autores){
        this.autores = autores;
        for (Autores autor : autores) {
            if (!autor.getLivros().contains(this)){
                autor.getLivros().add(this);
            }
        }
    }

    public String getIdiomas(){
        if (idiomas == null || idiomas.isEmpty()) {
            return "";
        } else {
            return String.valueOf(idiomas.get(0));
        }
    }

    @Override
    public String toString() {
        return "Titulo: " + titulo + '\n' +
                "Autor(es): " + '\n' + autores.stream()
                .map(autores -> autores.toString())
                .collect(Collectors.joining("\n")) + '\n' +
                "Idioma(es): " + idiomas.stream()
                .map(Idiomas::getIdioma)
                .collect(Collectors.joining(", ")) + '\n' +
                "Downloads" + contadorDownloads + "\n";
    }
}
