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

	/**
	 * Permite obtener un cliente mediante su id
	 * 
	 * @param id es el identificador unico de cada cliente, es un entero
	 * @return retorna un Optional de Cliente
	 */
	public Optional<Cliente> getCliente(int id) {
		return this.clientes.findById(id);
	}

	/**
	 * Permite obtener la lista de todos los clientes
	 * 
	 * @return retorna una lista de Cliente
	 */
	public List<Cliente> getAll() {
		return this.clientes.findAll();
	}

	/**
	 * Agrega un nuevo cliente
	 * 
	 * @param c una entidad Cliente
	 * @return retorna true si se agrego o false si no se pudo agregar
	 */
	public Boolean addCliente(Cliente c) {
		return this.clientes.save(c) != null;
	}

	/**
	 * Elimina un cliente
	 * 
	 * @param c una entidad Cliente
	 */
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
