package com.alura.literalura.service;

public interface IConversorJson {
    <T> T obterDados(String json, Class<T> classe);
}
