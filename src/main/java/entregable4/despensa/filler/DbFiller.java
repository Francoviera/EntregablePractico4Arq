package entregable4.despensa.filler;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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

@Configuration
public class DbFiller {

	private static ArrayList<Cliente> clientesLista = new ArrayList<Cliente>();
	private static ArrayList<Producto> productosLista = new ArrayList<Producto>();
	private static ArrayList<Pedido> pedidosLista = new ArrayList<Pedido>();

	@Bean
	public CommandLineRunner initDb(ProductoService productos, ClienteService clientes, PedidoService pedidos,
			ItemPedidoService itempedidos, ProductoStockService stock) {
		return args -> {
			IntStream.range(0, 50).forEach(i -> {
				int j = i + 1;

				Producto p = new Producto("nombre" + j, "marca" + j);
				productos.addProducto(p);

				Cliente c = new Cliente("nombre" + j, "apellido" + j, (int) (Math.random() * 99999999));
				clientes.addCliente(c);

				productosLista.add(p);
				ProductoStock prodStock = new ProductoStock(p, (int) (Math.random() * 50), Math.round((Math.random() * 200) * 100.0) / 100.0);
				stock.addProductoStock(prodStock);

				clientesLista.add(c);

				Pedido pedido = new Pedido(clientesLista.get((int) (Math.random() * clientesLista.size())));

				pedidos.addPedido(pedido);
				pedidosLista.add(pedido);

				ItemPedido item = new ItemPedido(productosLista.get((int) (Math.random() * productosLista.size())),
						(int) (Math.random() * 3), pedidosLista.get((int) (Math.random() * pedidosLista.size())));

				itempedidos.addItemPedido(item);

			});
		};
	}
}
