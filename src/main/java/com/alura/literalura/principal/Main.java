package com.alura.literalura.principal;

import com.alura.literalura.model.Autores;
import com.alura.literalura.model.Idiomas;
import com.alura.literalura.model.Livros;
import com.alura.literalura.repository.RepositorioAutores;
import com.alura.literalura.repository.RepositorioLivros;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ObterLivro;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    Scanner sc = new Scanner(System.in);
    private List<Livros> livros = new ArrayList<>();
    private List<Autores> autores = new ArrayList<>();

    @Autowired
    private RepositorioLivros repositorioLivros;

    @Autowired
    private RepositorioAutores repositorioAutores;

    public Main(RepositorioLivros repositorioLivros, RepositorioAutores repositorioAutores) {
        this.repositorioLivros = repositorioLivros;
        this.repositorioAutores = repositorioAutores;
    }

    String linkBusca = "https://gutendex.com/books/?search=";

    public void menu() {
        int option = -1;
        while (option != 0) {
            System.out.println("""
            1 - Buscar livro por título.
            2 - Listar livros registrados.
            3 - Listar autores registrados.
            4 - Listar autores vivos por ano.
            5 - Listar livros por idioma.
            6 - Top 10 livros mais baixados.
            7 - Busca por autor.
            8 - Verificar percentual de livros por idioma
            
            0 - Sair
            """);

            option = sc.nextInt();
            sc.nextLine();

            switch (option){
                case 1:
                    buscaLivro();
                    break;
                case 2:
                    listarLivrosBuscados();
                    break;
                case 3:
                    listarAutoresBuscados();
                    break;
                case 4:
                    autoresVivos();
                    break;
                case 5:
                    top10Livros();
                    break;
                case 6:
                    buscarAutor();
                    break;
                case 0:
                    System.out.println("Obrigado por usar nosso programa, volte sempre!...");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente!\n");
            }
        }
    }

    public void buscaLivro() {
        System.out.println("Insira o titulo do livro para realizar a busca: ");
        String buscaApi = linkBusca + sc.nextLine().replace(" ", "%20");
        ObterLivro obterLivro = new ObterLivro(repositorioLivros, repositorioAutores);
        obterLivro.obterDadosLivros(buscaApi);
    }

    private void listarLivrosBuscados(){
            livros = repositorioLivros.findAll();
            livros.stream().forEach(System.out::println);
    }
    private void listarAutoresBuscados() {
    autores = repositorioAutores.findAll();
    autores.stream().forEach(System.out::println);
    }

    private void autoresVivos() {
    autores = repositorioAutores.findAll();
    System.out.println("Digite o ano para verificar os autores vivos:");
    int year = sc.nextInt();
    sc.nextLine();
    repositorioAutores.findLivingAuthorsInYear(year).stream().forEach(System.out::println);
    }

    private void top10Livros() {
        ObterLivro obtem = new ObterLivro(repositorioLivros, repositorioAutores);
        obtem.obterTop10(linkBusca);
    }

    private void buscarAutor() {
        System.out.println("Digite APENAS o nome OU sobrenome do autor que deseja buscar informações: ");
        String autor = sc.nextLine().replace(" ", "%20");
        ObterLivro obtem = new ObterLivro(repositorioLivros, repositorioAutores);
        obtem.obterAutor(autor);
    }
}
