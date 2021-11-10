package entregable4.despensa.entities;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPedido;

	@ManyToOne
	@JoinColumn(name = "idCliente")
	private Cliente cliente;

	@Column(nullable = true)
	private Date momentoCompra;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "idItem")
	private List<ItemPedido> pedidos;

	@Column(nullable = true)
	private double precioTotal;

	public Pedido(Cliente cliente, List<ItemPedido> itemspedidos) {
		super();
		this.cliente = cliente;
		this.pedidos= new ArrayList<ItemPedido>(itemspedidos);
	}
	
	public Pedido(Cliente cliente, List<ItemPedido> itemspedidos, Date momentoCompra) {
		super();
		this.cliente = cliente;
		this.pedidos= new ArrayList<ItemPedido>(itemspedidos);
		this.momentoCompra=momentoCompra;
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

	public Date getMomentoCompra() {
		return momentoCompra;
	}

	public void setMomentoCompra(Date momentoCompra) {
		this.momentoCompra = momentoCompra;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public List<ItemPedido> getPedidos() {
		return pedidos;
	}

	@Override
	public String toString() {
		return "Pedido [cliente=" + cliente + ", momentoCompra=" + momentoCompra + "]";
	}

}
