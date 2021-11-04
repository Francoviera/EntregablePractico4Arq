package entregable4.despensa.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entregable4.despensa.entities.Cliente;
import entregable4.despensa.entities.ItemPedido;
import entregable4.despensa.entities.Pedido;
import entregable4.despensa.repository.ClienteRepository;
import entregable4.despensa.repository.ItemPedidoRepository;
import entregable4.despensa.repository.PedidoRepository;
import entregable4.despensa.order.SortByDate;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clientes;
	
	private ItemPedidoRepository itemPedidos;

	

	public Optional<Cliente> getCliente(int id) {
		return this.clientes.findById(id);
	}

	public List<Cliente> getAll() {
		return this.clientes.findAll(); // pasar a paginado
	}
	
//	public ArrayList<String> getTotalOfPedidosByCliente() {
//		ArrayList<String> reporte= new ArrayList<String>();
//		List<ItemPedido> itemPedidos= this.itemPedidos.findAll();
//		Collections.sort(itemPedidos, new SortByDate());
//		for (ItemPedido itemPedido : itemPedidos) {
//			reporte.add("Cliente: "+ itemPedido.getPedido().getCliente().getNombre() +" "+ itemPedido.getPedido().getCliente().getNombre()+
//					"Total: "+ itemPedido.getPedido().getPrecioTotal());	
//		}
//		
//		return reporte;
//		
//	}
//	
//	public ArrayList<String> getSalesByDay() {
//		ArrayList<String> reporte= new ArrayList<String>();
//		List<ItemPedido> itemPedidos= this.itemPedidos.findAll();
//		Collections.sort(itemPedidos, new SortByDate());
//		for (ItemPedido itemPedido : itemPedidos) {
//			reporte.add("Cliente: "+ itemPedido.getPedido().getCliente().getNombre() +" "+ itemPedido.getPedido().getCliente().getNombre()+
//					"Total: "+ itemPedido.getPedido().getPrecioTotal());	
//		}
//		
//		return reporte;
//		
//	}

	public Boolean addCliente(Cliente c) {
		return this.clientes.save(c) != null;
	}

	public void deleteCliente(Cliente c) {
		this.clientes.delete(c);
	}
}
