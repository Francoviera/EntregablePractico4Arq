package entregable4.despensa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class ProductoStock {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idProductoStock;
	@OneToOne
	@JoinColumn(name = "idProducto")
	private Producto producto; // RELACION 1:1
	@Column
	private int stock;
	@Column
	private double precio;


	public ProductoStock() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductoStock(Producto producto, int stock, double precio) {
		super();
		this.producto = producto;
		this.stock = stock;
		this.precio=precio;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getId() {
		return idProductoStock;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	
}
