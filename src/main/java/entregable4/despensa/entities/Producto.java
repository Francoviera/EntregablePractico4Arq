package entregable4.despensa.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProducto;
	@Column
	private String nombreProducto;
	@Column
	private String marca;

	@OneToMany(mappedBy = "producto")
	private List<ItemPedido> itemspedidos;

	public Producto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Producto(String nombreProducto, String marca) {
		super();
		this.nombreProducto = nombreProducto;
		this.marca = marca;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public int getId() {
		return idProducto;
	}

	@Override
	public String toString() {
		return "Producto [nombreProducto=" + nombreProducto + ", marca=" + marca + "]";
	}

	@Override
	public boolean equals(Object obj) {
		Producto p = (Producto) obj;
		return p.getNombreProducto().equals(this.getNombreProducto()) && p.getMarca().equals(this.getMarca());
	}
}
