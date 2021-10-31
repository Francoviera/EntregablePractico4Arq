package entregable4.despensa.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idCliente; // id

	@Column
	private String nombre; // nombre

	@Column
	private String apellido; // apellido

	@Column
	private int dni; // dni (equals)
	
	@ManyToMany
	@JoinColumn(name = "idPedido")
	List<Pedido> pedidos;
	
	@ManyToOne
	@JoinColumn(name = "idDespensa")
	private Despensa negocio;

	public Cliente() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Cliente(String nombre, String apellido, int dni, Despensa despensa) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.negocio=despensa;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public int getId() {
		return idCliente;
	}

	public Despensa getNegocio() {
		return negocio;
	}

	public void setNegocio(Despensa negocio) {
		this.negocio = negocio;
	}

	@Override
	public boolean equals(Object obj) {
		Cliente c = (Cliente) obj;
		return c.getDni() == this.getDni();
	}
}
