package entregable4.despensa.filler;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
	private static ArrayList<ItemPedido> itemPedidosLista = new ArrayList<ItemPedido>();

	/**
	 * Se persisten datos de manera automatica mediante un forEach y se aleatorizan
	 * sus parametros con el iterador del mismo
	 * 
	 * @param productos   un Servicio de productos
	 * @param clientes    un Servicio de clientes
	 * @param pedidos     un Servicio de pedidos
	 * @param itempedidos un Servicio de itempedidos
	 * @param stock       un Servicio de productosstock
	 * @return retorna un CommandLinerRunner
	 */
	@Bean
	public CommandLineRunner initDb(ProductoService productos, ClienteService clientes, PedidoService pedidos,
			ItemPedidoService itempedidos, ProductoStockService stock) {
		return args -> {
			IntStream.range(0, 20).forEach(i -> {
				int j = i + 1;

				Producto p = new Producto("nombre" + j, "marca" + j);
				productos.addProducto(p);

				Cliente c = new Cliente("nombre" + j, "apellido" + j, (int) (Math.random() * 99999999));
				clientes.addCliente(c);

				productosLista.add(p);
				ProductoStock prodStock = new ProductoStock(p, (int) (Math.random() * 50),
						Math.round((Math.random() * 200) * 100.0) / 100.0);
				stock.addProductoStock(prodStock);

				clientesLista.add(c);

				ItemPedido item = new ItemPedido(productosLista.get((int) (Math.random() * productosLista.size() - 1)),
						(int) (Math.random() * 2) + 1);

				itemPedidosLista.add(item);
				itempedidos.addItemPedido(item);

				Random rand = new Random();
				ItemPedido randomElement = itemPedidosLista.get(rand.nextInt(itemPedidosLista.size()));
				ItemPedido randomElement2 = itemPedidosLista.get(rand.nextInt(itemPedidosLista.size()));

				List<ItemPedido> sub = new ArrayList<ItemPedido>();
				sub.add(randomElement);
				sub.add(randomElement2);
				Pedido pedido;
				if (Math.random() > 0.5) {
					pedido = new Pedido(clientesLista.get(i), sub);
				} else {
					Long d = (long) (System.currentTimeMillis() * Math.random());
					Date date = new Date(d);
					pedido = new Pedido(clientesLista.get(i), sub, date);
				}

				pedidos.addPedido(pedido);

			});
		};
	}
}
