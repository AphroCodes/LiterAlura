package com.alura.literalura.service;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.RepositorioAutores;
import com.alura.literalura.repository.RepositorioLivros;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ObterLivro {
    ConsumoApi consumoApi = new ConsumoApi();
    ConversorJson conversorJson = new ConversorJson();

    @Autowired
    private RepositorioLivros repositorioLivros;

    @Autowired
    RepositorioAutores repositorioAutores;

    public ObterLivro(RepositorioLivros repositorioLivros, RepositorioAutores repositorioAutores) {
        this.repositorioLivros = repositorioLivros;
        this.repositorioAutores = repositorioAutores;
    }

    @Transactional
    public void obterDadosLivros(String livro) {
        String json = consumoApi.obterDados(livro);
        Dados dados = conversorJson.obterDados(json, Dados.class);
        try {
            DadosLivros dadosLivros = dados.livros().get(0);

            Livros livros = new Livros(dadosLivros);
            if (repositorioLivros.findByTitulo(livros.getTitulo()) != null) {
                System.out.println("Livro existente no banco de dados, aguarde um momento: " + "\n");
                System.out.println(repositorioLivros.findByTitulo(livros.getTitulo()).toString());
            } else {
                List<Autores> autores = new ArrayList<>();
                for (DadosAutores dadosAutores : dadosLivros.autores()) {
                    Autores autor = new Autores(dadosAutores);
                    List<Autores> autoresExistentes = repositorioAutores.findByNome(autor.getNome());
                    if (autoresExistentes.isEmpty()){
                        autor = repositorioAutores.save(autor);
                    } else {
                        autor = autoresExistentes.get(0);
                    }
                    autores.add(autor);
                }
                livros.setAutores(autores);
                repositorioLivros.save(livros);
                System.out.println(livros);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Livro não encontrados na base de dados." + "\n");
        }
    }

    public void obterTop10(String link) {
        String json = consumoApi.obterDados(link);
        Dados dados = conversorJson.obterDados(json, Dados.class);
        for (int i = 0; i <10; i++) {
            try {
                DadosLivros dadosLivros = dados.livros().get(i);
                Livros livros = new Livros(dadosLivros);
                if (repositorioLivros.findByTitulo(livros.getTitulo()) != null) {
                    System.out.println(repositorioLivros.findByTitulo(livros.getTitulo()).toString());
                } else {
                    List<Autores> autores = new ArrayList<>();
                    for (DadosAutores dadosAutores : dadosLivros.autores()) {
                        Autores autor = new Autores(dadosAutores);
                        List<Autores> autoresExistentes = repositorioAutores.findByNome(autor.getNome());
                        if (autoresExistentes.isEmpty()){
                            autor = repositorioAutores.save(autor);
                        } else {
                            autor = autoresExistentes.get(0);
                        }
                        autores.add(autor);
                    }
                    livros.setAutores(autores);
                    repositorioLivros.save(livros);
                    System.out.println(livros);
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Livro não encontrados na base de dados." + "\n");
            }
        }
    }

    public void obterAutor (String buscarAutor) {
        String json = consumoApi.obterDados("https://gutendex.com/books/?search=" + buscarAutor);
        Dados dados = conversorJson.obterDados(json, Dados.class);
        try {
            DadosLivros dadosLivros = dados.livros().get(0);
            Livros livros = new Livros(dadosLivros);
            String busca = buscarAutor.toLowerCase();
            List<Autores> autoresEncontrados = repositorioAutores.findByNameContainingIgnoreCase(busca);
            if (!autoresEncontrados.isEmpty()) {
                System.out.println("Autores encontrados: " + "\n" + autoresEncontrados.get(0));
            } else {
                System.out.println("Autor não encontrado na base de dados.");
                Livros livroExistente = repositorioLivros.findByTitulo(livros.getTitulo());
                if (livroExistente != null) {
                List<Autores> autoresExistentes = livroExistente.getAutores()
                        .stream()
                        .filter(a -> a.getNome().contains(busca))
                        .collect(Collectors.toList());
                    System.out.println(autoresExistentes);
                    for (Autores autor : autoresExistentes) {
                        String nomeAutor = autor.getNome().toLowerCase().trim();
                        if (nomeAutor.contains(busca)) {
                            System.out.println("Autor encontrado na base de dados: " + "\n" + autor);
                            return;
                        }
                    }
                } else {
                    System.out.println("Autor não encontrado na base de dados.");
                    List<Autores> autores = new ArrayList<>();
                    for (DadosAutores dadosAutores : dadosLivros.autores()) {
                        Autores autor = new Autores(dadosAutores);
                        List<Autores> autoresExistentes  = repositorioAutores.findByNome(autor.getNome());
                        if (autoresExistentes.isEmpty()) {
                            autor = repositorioAutores.save(autor);
                        } else {
                            autor = autoresExistentes.get(0);
                        }
                        autores.add(autor);
                    }
                    livros.setAutores(autores);
                    repositorioLivros.save(livros);
                    System.out.println("Novo livro inserido na base de dados: " + "\n" + livros);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Autor não encontrado na base de dados.\n");
        }
    }
}
