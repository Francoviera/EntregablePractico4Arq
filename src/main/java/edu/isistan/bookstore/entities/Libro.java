package edu.isistan.bookstore.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cascade;

@Entity
public class Libro {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String nombre;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
//	@Cascade(CascadeType.PERSIST)
	private List<Autor> autores;

	@Override
	public String toString() {
		return "Libro [id=" + id + ", nombre=" + nombre + ", autores=" + autores + "]";
	}
	
	public Libro() {
		
	}

	public Libro(String nombre, List<Autor> autores) {
		super();
		this.nombre = nombre;
		this.autores = autores;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Autor> getAutores() {
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		autores = autores;
	}

}
