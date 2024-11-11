package com.literatura.challenge_2_back_literatura;
import com.literatura.challenge_2_back_literatura.Livraria.Livraria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import com.literatura.challenge_2_back_literatura.repository.IAutorRepository;
import com.literatura.challenge_2_back_literatura.repository.ILivroRepository;

@SpringBootApplication
public class Challenge2BackLiteraturaApplicationConsole implements CommandLineRunner {

	@Autowired
	private ILivroRepository livroRepository;
	@Autowired
	private IAutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(Challenge2BackLiteraturaApplicationConsole.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Livraria livraria = new Livraria(livroRepository, autorRepository);
		livraria.consumo();

	}
}
