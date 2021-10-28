package edu.isistan.bookstore.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.isistan.bookstore.entities.Autor;
import edu.isistan.bookstore.entities.Libro;
import edu.isistan.bookstore.repository.LibroRepository;
@Service
public class LibroService {
	@Autowired
	private LibroRepository Libros; 
	
	public Optional<Libro> getLibro(int id) {
		return this.Libros.findById(id);
		
	}
@Transactional
	public boolean addLibro(Libro l) {
		this.Libros.save(l);
		return true;
		
	}

	public List<Libro> getLibros() {
		// TODO Auto-generated method stub
		return this.Libros.findAll();
	}
}
