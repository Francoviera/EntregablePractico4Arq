package entregable4.despensa.services;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entregable4.despensa.DTO.ReporteCompras;
import entregable4.despensa.DTO.ReporteVentasPorDia;
import entregable4.despensa.entities.Cliente;
import entregable4.despensa.entities.ItemPedido;
import entregable4.despensa.entities.Pedido;
import entregable4.despensa.repository.ClienteRepository;
import entregable4.despensa.repository.PedidoRepository;
import entregable4.despensa.order.SortByDate;

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

	// ya esta
	public ArrayList<ReporteCompras> getTotalOfPedidosByCliente() {
		// ESTE RESUELVE EL PUNTO 3, PARA CADA CLIENTE, NOMBRE Y TOTAL GASTADO
		ArrayList<ReporteCompras> reporte = new ArrayList<ReporteCompras>();
		List<Pedido> listPedidos = this.pedidos.findAll();
//		System.out.println(listPedidos);
		for (Pedido pedido : listPedidos) {
			reporte.add(new ReporteCompras(pedido.getCliente().getNombre(), pedido.getCliente().getApellido(),
					pedido.getPrecioTotal()));
		}

		return reporte;

	}



	public Boolean addCliente(Cliente c) {
		return this.clientes.save(c) != null;
	}

	public void deleteCliente(Cliente c) {
		this.clientes.delete(c);
	}
}
