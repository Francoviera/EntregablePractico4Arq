package entregable4.despensa.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idPedido;

	@ManyToMany
	@JoinColumn(name = "idCliente")
	private Cliente cliente;

	@Column(nullable = true)
	private Timestamp momentoCompra;

	@ManyToMany
	@JoinColumn(name = "idItem")
	private List<ItemPedido> pedidos;

	@Column(nullable = true)
	private int precioTotal;

	public Pedido(Cliente cliente) {
		super();
		this.cliente = cliente;
	}

	public Pedido() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getId() {
		return idPedido;
	}

	public Timestamp getMomentoCompra() {
		return momentoCompra;
	}

	public void setMomentoCompra(Timestamp momentoCompra) {
		this.momentoCompra = momentoCompra;
	}

	public int getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(int precioTotal) {
		this.precioTotal = precioTotal;
	}

}
