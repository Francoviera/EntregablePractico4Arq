package entregable4.despensa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entregable4.despensa.DTO.MasVendido;
import entregable4.despensa.entities.ItemPedido;
import entregable4.despensa.entities.Producto;
import entregable4.despensa.repository.ItemPedidoRepository;
import entregable4.despensa.repository.ProductoRepository;

@Service
public class ProductoService {

	@Autowired
	private ProductoRepository productos;
	@Autowired
	private ItemPedidoRepository itemPedidos;

	public Optional<Producto> getProducto(int id) {
		return this.productos.findById(id);
	}

	public List<Producto> getProductos() {
		return this.productos.findAll();
	}

	public boolean addProducto(Producto p) {
		return this.productos.save(p) != null;
	}

	public void deleteProducto(Producto p) {
		this.productos.delete(p);
	}

	/**
	 * Metodo que retorna el producto con mayor cantidad de ventas 
	 * Responde a 5) Implemente una consulta para saber cuál fue el producto más vendido.
	 * 
	 * @return retorna un Optional del DTO MasVendido
	 */
	public Optional<MasVendido> getProductoMasVendido() {
		List<ItemPedido> items = this.itemPedidos.obtenerMasVendido();
		if (items==null) {
			return Optional.ofNullable(null);
		}

		ItemPedido item = items.get(0);
		int cantidad = 0;
		for (ItemPedido i : items) {
			if (i.getProducto().getNombreProducto().equals(item.getProducto().getNombreProducto())
					&& i.getProducto().getMarca().equals(item.getProducto().getMarca())) {
				cantidad += i.getCantidad();
			}
		}

		MasVendido masVendido = new MasVendido(item.getProducto().getNombreProducto(), item.getProducto().getMarca(),
				cantidad);

		return Optional.ofNullable(masVendido);
	}
}
