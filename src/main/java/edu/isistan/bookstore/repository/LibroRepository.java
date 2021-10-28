package edu.isistan.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.isistan.bookstore.entities.Libro;

public interface LibroRepository extends JpaRepository<Libro, Integer> {

}
