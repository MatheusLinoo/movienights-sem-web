package br.com.alura.movienights;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.alura.movienights.model.DadosSerie;
import br.com.alura.movienights.service.ConsumoApi;
import br.com.alura.movienights.service.ConverteDados;

@SpringBootTest
class MovienightsApplicationTests implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MovienightsApplicationTests.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=74c3f45b");
		System.out.println(json);
		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);
	}
}
