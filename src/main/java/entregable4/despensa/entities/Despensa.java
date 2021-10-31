package entregable4.despensa.entities;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Despensa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idDespensa; // identificador
	
	@Column
	private String nombre; // nombre
	
	@OneToMany(mappedBy = "despensa")
	private List<ProductoStock> stock; // lista de productos en stock
	
	@OneToMany(mappedBy = "negocio") // lista de clientes de la despensa
	private List<Cliente> clientes;

	public Despensa() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Despensa(String nombre) {
		super();
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return idDespensa;
	}

	
	// pasar a implementation
//	/**
//	 * Permite agregar un producto al stock
//	 * @param producto un objeto producto
//	 * @param cantidadAgregar la cantidad que se desea agregar
//	 */
//	public void addStock(Producto producto, int cantidadAgregar) {
//		for (int i = 0; i < stock.size(); i++) {
//			if (producto.getId() == stock.get(i).getProducto().getId()) {
//				stock.get(i).setStock(stock.get(i).getStock() + cantidadAgregar);
//				return;
//			}
//		}
//		ProductoStock productoStock = new ProductoStock(producto, cantidadAgregar);
//		stock.add(productoStock);
//	}
//
//	/**
//	 * Permite agregar un cliente a la lista de clientes
//	 * @param cliente recibe un objeto cliente
//	 */
//	public void addCliente(Cliente cliente) {
//		if (!clientes.contains(cliente)) {
//			clientes.add(cliente);
//		}
//	}

}
