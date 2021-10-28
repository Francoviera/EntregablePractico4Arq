package edu.isistan.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.isistan.bookstore.entities.Libro;
import edu.isistan.bookstore.services.LibroService;

//@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})


@RestController
@RequestMapping("/libro")
public class BookController {

//	private static Logger LOG = LoggerFactory.logger(BookStore.class);
	@Autowired
	private LibroService serviciolibros;

	@GetMapping("")
	public List<Libro>getAll() {
		return this.serviciolibros.getLibros();
	}
	@GetMapping("/{id}")
	public ResponseEntity<Libro> getLibro(@PathVariable("id")int id){
		Optional<Libro> Libro =serviciolibros.getLibro(id);
		if(Libro.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(Libro.get(),HttpStatus.OK);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<Libro> addLibro(@RequestBody Libro l){
		boolean ok=  serviciolibros.addLibro(l);
		if(!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

		}else {
			return new ResponseEntity<>(l,HttpStatus.OK);
		}
	}
}
