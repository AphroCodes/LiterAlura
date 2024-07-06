package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosAutores (@JsonAlias("nome") String nome,
                            @JsonAlias("ano_nascimento") Integer anoNascimento,
                            @JsonAlias("ano_falecimento") Integer anoFalecimento){

}
