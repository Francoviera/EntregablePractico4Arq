package entregable4.despensa.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entregable4.despensa.DTO.ReporteCompras;
import entregable4.despensa.entities.Cliente;
import entregable4.despensa.entities.Pedido;
import entregable4.despensa.repository.ClienteRepository;
import entregable4.despensa.repository.PedidoRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clientes;

	@Autowired
	private PedidoRepository pedidos;

	public Optional<Cliente> getCliente(int id) {
		return this.clientes.findById(id);
	}

	public List<Cliente> getAll() {
		return this.clientes.findAll(); // pasar a paginado
	}

	public Boolean addCliente(Cliente c) {
		return this.clientes.save(c) != null;
	}

	public void deleteCliente(Cliente c) {
		this.clientes.delete(c);
	}

	/**
	 * Genera un reporte del total de dinero que cada cliente invirtió en el negocio
	 * 
	 * @return retorna un Arraylist de DTO ReporteCompras que incluye nombre y
	 *         apellido del cliente y total gastado
	 */
	public ArrayList<ReporteCompras> getTotalOfPedidosByCliente() {
		ArrayList<ReporteCompras> reporte = new ArrayList<ReporteCompras>();
		List<Pedido> listPedidos = this.pedidos.findAll();
		for (Pedido pedido : listPedidos) {
			reporte.add(new ReporteCompras(pedido.getCliente().getNombre(), pedido.getCliente().getApellido(),
					pedido.getPrecioTotal()));
		}

		return reporte;

	}
}
