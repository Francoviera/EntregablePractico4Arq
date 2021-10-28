package edu.isistan.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.isistan.bookstore.entities.Autor;

public interface AutorRepository extends JpaRepository<Autor, Integer> {

}
