package entregable4.despensa.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

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

public class TestApi {
	private static ClienteService clienteService;
	private static PedidoService pedidoService;
	private static ItemPedidoService itemPedidoService;
	private static ProductoService productoService;
	private static ProductoStockService productoStockService;

	@BeforeAll
	public static void initClass() {

	}

	/**
	 * ESTO HAY QUE VERIFICARLO POR COMPLETO PORQUE NO SE LE PUEDEN PASAR LAS INSTANCIAS DE LOS SERVICIOS
	 */
	@Test
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
		
		List<ProductoStock>stockList=productoStockService.getProductoStock();
		
		for(ProductoStock prod: stockList ) {
			if(prod.getProducto().getNombreProducto().equals(p1.getNombreProducto())) {
				assertEquals(prod.getStock(), stock.getStock()- item1.getCantidad());
			}
		}
	}
}
