package br.com.alura.movienights;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.alura.movienights.principal.Principal;

@SpringBootTest
class MovienightsApplicationTests implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MovienightsApplicationTests.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();

		// List<DadosTemporada> temporadas = new ArrayList<>();

		// for (int i = 1; i<=dados.totalTemporadas(); i++) {
		// 	json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=" + i + "&apikey=74c3f45b");
		// 	DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
		// 	temporadas.add(dadosTemporada);
		// }
		// temporadas.forEach(System.out::println);
	}
}
