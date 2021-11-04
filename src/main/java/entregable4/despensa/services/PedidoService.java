package entregable4.despensa.services;

import java.sql.Timestamp;
import java.util.ArrayList;
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
	
	public List<Pedido> getPedidosByCliente(int idCliente){
		return this.pedidos.findPedidoOfCliente(idCliente);
	}

	public List<Pedido> getPedidos() {
		return this.pedidos.findAll();
	}

	public boolean addPedido(Pedido p) {
		// generar fecha aleatoria
		long offset = Timestamp.valueOf("2021-10-01 00:00:00").getTime();
		long end = Timestamp.valueOf("2022-01-01 00:00:00").getTime();
		long diff = end - offset + 1;
		Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));
		
		p.setMomentoCompra(rand);
		
		// traer los pedidos del cliente
		List<Pedido> pedidos = this.getPedidosByCliente(p.getCliente().getId());
		
		// filtrar los que son del dia del pedido p
		
		// mirar por itempedido

		
		
		return this.pedidos.save(p) != null;
	}

	public void deletePedido(Pedido p) {
		this.pedidos.delete(p);
	}
}
