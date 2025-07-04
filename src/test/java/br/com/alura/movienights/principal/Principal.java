package br.com.alura.movienights.principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.alura.movienights.model.DadosEpisodio;
import br.com.alura.movienights.model.DadosSerie;
import br.com.alura.movienights.model.DadosTemporada;
import br.com.alura.movienights.model.Episodio;
import br.com.alura.movienights.service.ConsumoApi;
import br.com.alura.movienights.service.ConverteDados;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();


    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=74c3f45b";

    public void exibeMenu(){
        System.out.println("Digite o nome da série");
        var nomeSerie = leitura.nextLine();
		var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i<=dados.totalTemporadas(); i++) {
			json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

        // for(int i = 0; i < dados.totalTemporadas(); i++){
        //     List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
        //     for(int j = 0; j < episodiosTemporada.size(); j++){
        //         System.out.println(episodiosTemporada.get(j).titulo());
        //     }
        // }

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
            .flatMap(t -> t.episodios().stream())
            .collect(Collectors.toList());

        System.out.println("\nTop 10 episódios");
        dadosEpisodios.stream()
            .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
            .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
            .limit(10)
            .map(e -> e.titulo().toUpperCase())
            .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
            .flatMap(t -> t.episodios().stream()
                .map(d -> new Episodio(t.numero(), d))
            ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        // System.out.println("Digite o trecho do título que você gostaria de pesquisar:");
        // var trechoTitulo = leitura.nextLine();
        // Optional<Episodio> episodioBuscado = episodios.stream()
        //     .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
        //     .findFirst();
        // if (episodioBuscado.isPresent()){
        //     System.out.println("Episódio encontrado!");
        //     System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
        // } else {
        //     System.out.println("Episodio não encontrado");
        // }

    //     System.out.println("Que ano você gostaria de pesquisar?");
    //     var ano = leitura.nextInt();
    //     leitura.nextLine();
        
    //     LocalDate dataBusca = LocalDate.of(ano, 1, 1);

    //     DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    //     episodios.stream()
    //         .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
    //         .forEach(e -> System.out.println("Temporada: " + e.getTemporada() + " Episodio: " + e.getTitulo() + " Data de Lançamento: " + e.getDataLancamento().format(formatador)
    //         ));

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
            .filter(e -> e.getAvaliacao() > 0.0)
            .collect(Collectors.groupingBy(Episodio::getTemporada, 
            Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
            .filter(e -> e.getAvaliacao() > 0.0)
            .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor episódio: " + est.getMax());
        System.out.println("Pior episódio: " + est.getMin());
        System.out.println("Total de episódios avaliados: " + est.getCount());
    }
}
