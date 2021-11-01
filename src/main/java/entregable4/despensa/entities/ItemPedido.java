package entregable4.despensa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class ItemPedido {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idItem;
	@ManyToOne
	@JoinColumn(name = "idProducto")
	private Producto producto; // LO QUE QUIERE COMPRAR
	private int cantidad; // LA CANTIDAD QUE QUIERE COMPRAR
	@ManyToMany
	@JoinColumn(name = "idPedido")
	private Pedido pedido;
	@Column(nullable = true)
	private int precioTotalItem;

	public ItemPedido() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemPedido(Producto producto, int cantidad, Pedido pedido) {
		super();
		this.producto = producto;
		this.cantidad = cantidad;
		this.pedido = pedido;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public int getId() {
		return idItem;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public int getPrecioTotalItem() {
		return precioTotalItem;
	}

	public void setPrecioTotalItem(int precioTotalItem) {
		this.precioTotalItem = precioTotalItem;
	}

}
