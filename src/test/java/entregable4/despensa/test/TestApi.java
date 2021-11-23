package entregable4.despensa.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import entregable4.despensa.entities.Cliente;
import entregable4.despensa.entities.ItemPedido;
import entregable4.despensa.entities.Pedido;
import entregable4.despensa.entities.Producto;
import entregable4.despensa.entities.ProductoStock;
import entregable4.despensa.services.ClienteService;
import entregable4.despensa.services.ItemPedidoService;
import entregable4.despensa.services.PedidoService;
import entregable4.despensa.services.ProductoService;
import entregable4.despensa.services.ProductoStockService;

@SpringBootTest
class TestApi {
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private ItemPedidoService itemPedidoService;
	@Autowired
	private ProductoService productoService;
	@Autowired
	private ProductoStockService productoStockService;

	public static void initClass() {

	}

	@Test
	@Order(1)
	public void testStock() {
		// creo un cliente
		Cliente c1 = new Cliente("Martin", "Aguirre", 39116326);
		// me creo un producto
		Producto p1 = new Producto("Sardinas", "Marolio");
		// lo meto en un itempedido
		ItemPedido item1 = new ItemPedido(p1, 1);
		// lo agrego al stock
		ProductoStock stock = new ProductoStock(p1, 10, 5.0);
		// creo un pedido
		ArrayList<ItemPedido> arr = new ArrayList<ItemPedido>();
		arr.add(item1);
		Pedido pedido = new Pedido(c1, arr);
		// los agrego
		clienteService.addCliente(c1);
		productoService.addProducto(p1);
		itemPedidoService.addItemPedido(item1);
		productoStockService.addProductoStock(stock);
		pedidoService.addPedido(pedido);

		List<ProductoStock> stockList = productoStockService.getProductoStock();

		for (ProductoStock prod : stockList) {
			if (prod.getProducto().getNombreProducto().equals(p1.getNombreProducto())) {
				assertEquals(prod.getStock(), stock.getStock() - item1.getCantidad());
			}
		}
	}
}

