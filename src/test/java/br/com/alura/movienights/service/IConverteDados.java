package br.com.alura.movienights.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
