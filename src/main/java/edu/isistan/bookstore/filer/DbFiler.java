package edu.isistan.bookstore.filer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.isistan.bookstore.entities.Autor;
import edu.isistan.bookstore.entities.Libro;
import edu.isistan.bookstore.repository.LibroRepository;

@Configuration
public class DbFiler {
	
	@Bean
	public CommandLineRunner initDb(LibroRepository libro) {
		return args  -> {
			IntStream.range(0, 10).forEach(i -> {
				List<Autor> autores= new ArrayList<>();
				autores.add(new Autor("pepe"+i));
				Libro l= new Libro("pepe"+i, autores);
				libro.save(l);
			});
		};
	};

}
