package br.com.alura.movienights.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(
    @JsonAlias("Title")String titulo,
    @JsonAlias("Episode")Integer numero,
    @JsonAlias("Released")String dataLancamento,
    @JsonAlias("imdbRating")String avaliacao) {
}
