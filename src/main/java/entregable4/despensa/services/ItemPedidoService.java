package entregable4.despensa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entregable4.despensa.DTO.MasVendido;
import entregable4.despensa.entities.Cliente;
import entregable4.despensa.entities.ItemPedido;
import entregable4.despensa.repository.ItemPedidoRepository;

@Service
public class ItemPedidoService {
	@Autowired
	private ItemPedidoRepository itemPedidos;

	public Optional<ItemPedido> getItemPedido(int id) {
		return this.itemPedidos.findById(id);
	}

	public List<ItemPedido> getAll() {
		return this.itemPedidos.findAll(); // pasar a paginado
	}

	public Boolean addItemPedido(ItemPedido itempedido) {
		return this.itemPedidos.save(itempedido) != null;
	}

	public void deleteItemPedido(ItemPedido itempedido) {
		this.itemPedidos.delete(itempedido);
	}
	
	
}
