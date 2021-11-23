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

	/**
	 * Permite obtener un Item mediante su id
	 * 
	 * @param id es el identificador unico de cada Item, es un entero
	 * @return retorna un Optional de ItemPedido
	 */
	public Optional<ItemPedido> getItemPedido(int id) {
		return this.itemPedidos.findById(id);
	}

	/**
	 * Permite obtener la lista de todos los items
	 * 
	 * @return retorna una lista de ItemPedido
	 */
	public List<ItemPedido> getAll() {
		return this.itemPedidos.findAll(); // pasar a paginado
	}

	/**
	 * Permite agregar un item
	 * 
	 * @param itempedido una entidad de tipo ItemPedido
	 * @return retorna true si pudo agregarlo o false si no se logro agregar
	 */
	public Boolean addItemPedido(ItemPedido itempedido) {
		return this.itemPedidos.save(itempedido) != null;
	}

	/**
	 * Permite eliminar un item
	 * 
	 * @param itempedido una entidad de tipo ItemPedido
	 */
	public void deleteItemPedido(ItemPedido itempedido) {
		this.itemPedidos.delete(itempedido);
	}

}
