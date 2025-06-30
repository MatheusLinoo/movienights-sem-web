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
	}
}
