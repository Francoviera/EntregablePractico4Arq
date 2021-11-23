package entregable4.despensa.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;

import entregable4.despensa.DTO.MasVendido;
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
@TestMethodOrder(OrderAnnotation.class)
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

	@Test
	@Order(1)
	public void testStock() {
		System.out.println("1");
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
		assertTrue(pedidoService.addPedido(pedido)); // verifica que el pedido se agrega correctamente
		assertEquals(pedido.getPrecioTotal(), stock.getPrecio()); // verifica que el precio del pedido es el esperado

		List<ProductoStock> stockList = productoStockService.getProductoStock();

		for (ProductoStock prod : stockList) {
			if (prod.getProducto().getNombreProducto().equals(p1.getNombreProducto())) {
				assertEquals(prod.getStock(), stock.getStock() - item1.getCantidad());
			}
		}
	}

	@Test
	@Order(2)
	public void testSobreCargarPedidos() {
		System.out.println("2");
		// creo un cliente
		Cliente c1 = new Cliente("Martin", "Aguirre", 39116326);
		// me creo un producto
		Producto p1 = new Producto("Arroz", "Gallo");
		// lo meto en un itempedido
		ItemPedido item1 = new ItemPedido(p1, 4);
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
		assertFalse(pedidoService.addPedido(pedido)); // verifica que el pedido se rechaza
	}

	@Test
	@Order(3)
	public void testGetPedidoById() {
		System.out.println("3");
		Optional<Pedido> op = pedidoService.getPedido(1);
		Pedido p = op.get();
		assertEquals(p.getId(), 1);
	}

	@Test
	@Order(4)
	public void testGetProductoById() {
		System.out.println("4");
		Optional<Producto> op = productoService.getProducto(1);
		Producto producto = op.get();
		assertEquals(producto.getId(), 1);
	}

	@Test
	@Order(5)
	public void testCantidadProductos() {
		System.out.println("5");
		List<Producto> productos = productoService.getProductos();
		assertEquals(productos.size(), 22); // 20 del filler + 2 de los test
	}

	@Test
	@Order(6)
	public void testEliminarPedidos() {
		System.out.println("6");
		List<Pedido> pedidos=pedidoService.getPedidos();
		for(Pedido pedido: pedidos) {
			pedidoService.deletePedido(pedido);
		}
		List<Pedido> pedidos1=pedidoService.getPedidos();
		System.out.println("pedidos1 "+pedidos1);
		assertTrue(pedidos1.size()==0);
//		fail();
	}
	
	@Test
	@Order(7)
	public void testProductoMasVendido() {
		List<ItemPedido> listItemPedidos=itemPedidoService.getAll();
		for(ItemPedido i:listItemPedidos) {
			itemPedidoService.deleteItemPedido(i);
		}
		
		Producto papas= new Producto("papas", "ninguna");
		Producto azucar= new Producto("azucar", "chango");
		Producto agua = new Producto("agua", "eco de los andes");
		
		ProductoStock papasStock= new ProductoStock(papas, 30, 10.0);
		ProductoStock azucarStock= new ProductoStock(azucar, 30, 10.0);
		ProductoStock aguaStock= new ProductoStock(agua, 30, 10.0);
		
		
		ItemPedido item1= new ItemPedido(papas,1);
		ItemPedido item2= new ItemPedido(papas,2);
		ItemPedido item3= new ItemPedido(papas,2);
		ItemPedido item4= new ItemPedido(azucar,1);
		ItemPedido item5= new ItemPedido(azucar,2);
		ItemPedido item6= new ItemPedido(azucar,2);
		ItemPedido item7= new ItemPedido(agua,3);
		ItemPedido item8= new ItemPedido(agua,3);
		ItemPedido item9= new ItemPedido(agua,3);
		
		Cliente c1= new Cliente("Benjamin", "Hoffman", 12345632);
		Cliente c2= new Cliente("Pepito", "Hoffman", 12897632);
		Cliente c3= new Cliente("Juancito", "Hoffman", 12455632);
		
		productoService.addProducto(papas);
		productoService.addProducto(azucar);
		productoService.addProducto(agua);
		clienteService.addCliente(c1);
		clienteService.addCliente(c2);
		clienteService.addCliente(c3);
		
		productoStockService.addProductoStock(papasStock);
		productoStockService.addProductoStock(azucarStock);
		productoStockService.addProductoStock(aguaStock);
		
		itemPedidoService.addItemPedido(item1);
		itemPedidoService.addItemPedido(item2);
		itemPedidoService.addItemPedido(item3);
		itemPedidoService.addItemPedido(item4);
		itemPedidoService.addItemPedido(item5);
		itemPedidoService.addItemPedido(item6);
		itemPedidoService.addItemPedido(item7);
		itemPedidoService.addItemPedido(item8);
		itemPedidoService.addItemPedido(item9);
		
		ArrayList<ItemPedido> arr1 = new ArrayList<ItemPedido>();
		arr1.add(item1);
		arr1.add(item4);
		arr1.add(item7);
		ArrayList<ItemPedido> arr2 = new ArrayList<ItemPedido>();
		arr2.add(item2);
		arr2.add(item5);
		arr2.add(item8);
		ArrayList<ItemPedido> arr3 = new ArrayList<ItemPedido>();
		arr3.add(item3);
		arr3.add(item6);
		arr3.add(item9);
		
		Pedido p1= new Pedido(c1,arr1);
		Pedido p2= new Pedido(c2,arr2);
		Pedido p3= new Pedido(c3,arr3);
		
		pedidoService.addPedido(p1);
		pedidoService.addPedido(p2);
		pedidoService.addPedido(p3);

		Optional<MasVendido> op=productoService.getProductoMasVendido();
		MasVendido productoMasVendido=op.get();

		boolean condition= productoMasVendido.getNombreProducto().equals(agua.getNombreProducto()) &&
		productoMasVendido.getMarca().equals(agua.getMarca());
		
		assertTrue(condition);
	}
	
	@Test
	@Order(8)
	public void testFail() {
		fail();
	} 

}
