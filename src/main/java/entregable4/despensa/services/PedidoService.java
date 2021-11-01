package entregable4.despensa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entregable4.despensa.entities.Pedido;
import entregable4.despensa.repository.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidos;

	public Optional<Pedido> getPedido(int id) {
		return this.pedidos.findById(id);
	}

	public List<Pedido> getPedidos() {
		return this.pedidos.findAll();
	}

	public boolean addPedido(Pedido p) {
		return this.pedidos.save(p) != null;
	}

	public void deletePedido(Pedido p) {
		this.pedidos.delete(p);
	}
}
