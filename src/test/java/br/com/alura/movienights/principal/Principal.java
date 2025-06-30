package br.com.alura.movienights.principal;

import java.util.Scanner;

import br.com.alura.movienights.model.DadosSerie;
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
    }
}
