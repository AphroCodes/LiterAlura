package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivros (@JsonAlias("titulo") String titulo,
                           @JsonAlias("idiomas")List<Idiomas> idiomas,
                           @JsonAlias("contador_downloads") Integer contadorDownloads,
                           @JsonAlias("autores") List<DadosAutores> autores)
{}
