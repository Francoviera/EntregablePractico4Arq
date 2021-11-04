package entregable4.despensa.services;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entregable4.despensa.entities.ItemPedido;
import entregable4.despensa.entities.Pedido;
import entregable4.despensa.entities.ProductoStock;
import entregable4.despensa.repository.PedidoRepository;
import entregable4.despensa.repository.ProductoStockRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidos;

	@Autowired
	private ProductoStockRepository stock;

	public Optional<Pedido> getPedido(int id) {
		return this.pedidos.findById(id);
	}

	public List<Pedido> getPedidosByCliente(int idCliente, Date date) {
		return this.pedidos.findPedidoOfCliente(idCliente, date);
//		return null;
	}

	public List<Pedido> getPedidos() {
		return this.pedidos.findAll();
	}

	public boolean addPedido(Pedido p) {

		// ACA LE DEFINIMOS LA FECHA
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Date date = new Date(ts.getTime());
		p.setMomentoCompra(date);

		// ACA APLICO LA SECRETARIA DE COMERCIO
		List<ItemPedido> listaNueva = p.getPedidos();
		if (listaNueva != null) {
			// traer los pedidos del cliente
			List<Pedido> lista = this.getPedidosByCliente(p.getCliente().getId(), date);
			for (Pedido pedido : lista) {
				List<ItemPedido> listaAux = pedido.getPedidos();
				for (ItemPedido item : listaAux) {
					if (listaNueva.contains(item)) {
						int contador = item.getCantidad() + listaNueva.get(listaNueva.indexOf(item)).getCantidad();
						if (contador > 3) {
//							rechazar pedido;
							return false;
						}
					}
				}
			}

			double precioTotal = 0;
			for (ItemPedido item : listaNueva) {
				int cantidadPedir = item.getCantidad();

				int id = item.getProducto().getId();
				ProductoStock productoStock = stock.findStockByIdProducto(id);
				
				double precio = productoStock.getPrecio();
				int stockDisponible = productoStock.getStock();
				if (stockDisponible < cantidadPedir) {
					return false;
				} else {
					productoStock.setStock(stockDisponible - cantidadPedir);
					precioTotal += cantidadPedir * precio;
				}
			}

			p.setPrecioTotal(precioTotal);
		}

		return this.pedidos.save(p) != null;
	}

	public void deletePedido(Pedido p) {
		this.pedidos.delete(p);
	}
}
