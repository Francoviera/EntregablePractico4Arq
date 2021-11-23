package entregable4.despensa.services;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entregable4.despensa.DTO.ReporteVentasPorDia;
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

	public void deletePedido(Pedido p) {
		System.out.print("pedido " + p);
		this.pedidos.delete(p);
	}

	/**
	 * Permite agregar un pedido siempre y cuando el cliente no supere el limite de
	 * compra de 3 productos por pedido por dia
	 * 
	 * @param p un objeto pedido
	 * @return retorna true si el pedido pudo agregarse o false si no
	 */
	public boolean addPedido(Pedido p) {
		// ACA LE DEFINIMOS LA FECHA
		if (p.getMomentoCompra() == null) {
			Date date = this.definirHora();
			p.setMomentoCompra(date);
		}

		// ACA APLICO LA SECRETARIA DE COMERCIO
		List<ItemPedido> listaNueva = p.getPedidos();
		if (listaNueva != null) {
			// traer los pedidos del cliente
			if (!chequearCantidad(p, p.getMomentoCompra(), listaNueva)) {
				return false;
			}
			if (!venderPedido(p, listaNueva)) {
				return false;
			}
		} else {
			return false;
		}

		return this.pedidos.save(p) != null;
	}

	/**
	 * Define la hora actual en caso de que el pedido no venga con una fecha cargada
	 * 
	 * @return retorna un objeto Date
	 */
	private Date definirHora() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Date date = new Date(ts.getTime());
		return date;
	}

	/**
	 * Permite hacer la operacion de venta recorriendo todos los itemspedidos y
	 * asegurandose que haya stock disponible, ademas calcula el precio total del
	 * pedido
	 * 
	 * @param p          el pedido en cuestion
	 * @param listaNueva una lista con los itemspedidos (que son un producto y la
	 *                   cantidad a agregar)
	 * @return retorna true si la operación fue exitosa o false si no lo fue
	 */
	private boolean venderPedido(Pedido p, List<ItemPedido> listaNueva) {
		double precioTotal = 0;
		List<ProductoStock> list = stock.findAll();
		for (ItemPedido item : listaNueva) {
			int cantidadPedir = item.getCantidad();

			for (ProductoStock productoStock : list) {
				if (productoStock.getProducto().getId() == item.getProducto().getId()) {
					double precio = productoStock.getPrecio();
					int stockDisponible = productoStock.getStock();
					if (stockDisponible < cantidadPedir) {
						return false;
					} else {
						productoStock.setStock(stockDisponible - cantidadPedir);
						precioTotal += cantidadPedir * precio;
					}
				}
			}

		}

		p.setPrecioTotal(precioTotal);
		return true;
	}

	/**
	 * Verifica que el usuario no haya superado el limite de ventas establecido
	 * 
	 * @param p          un pedido
	 * @param date       la fecha en la que se realizo el pedido
	 * @param listaNueva la lista de los itemspedidos del pedido en cuestión
	 * @return retorna true si la operación fue exitosa o false si no lo fue
	 */
	private boolean chequearCantidad(Pedido p, Date date, List<ItemPedido> listaNueva) {
		List<Pedido> lista = this.getPedidosByCliente(p.getCliente().getId(), date);
		for (Pedido pedido : lista) {
			List<ItemPedido> listaAux = pedido.getPedidos();
			for (ItemPedido item : listaAux) {
				for (ItemPedido nueva : listaNueva) {
					if (nueva.getProducto().getId() == item.getProducto().getId()) {
						int contador = item.getCantidad() + nueva.getCantidad();
						if (contador > 3) {
//							rechazar pedido;
							return false;
						}
					}
				}

			}
		}
		return true;
	}

	/**
	 * Realiza un reporte de las ventas por dia incluyendo monto y cantidad de
	 * productos que se vendieron
	 * 
	 * @return retorna un ArrayList de ReporteVentasPorDia que es un Data Transfer
	 *         Object creado para esta operación
	 */
	public ArrayList<ReporteVentasPorDia> getSalesByDay() {
		ArrayList<ReporteVentasPorDia> reporte = new ArrayList<ReporteVentasPorDia>();
		List<Pedido> listPedidos = this.pedidos.findPedidosByDay();
		int cantidad = 0;
		double montototal = 0;
		Date momento = null;

		Calendar c = new GregorianCalendar();
		for (Pedido pedido : listPedidos) {
			Date aux = pedido.getMomentoCompra();
			Calendar c1 = new GregorianCalendar();
			c1.setTime(aux);
			if (momento == null) {
				montototal += pedido.getPrecioTotal();
				cantidad += pedido.getPedidos().size();
				momento = aux;
				c.setTime(momento);
			} else if (this.areEqualDays(c, c1)) {
				montototal += pedido.getPrecioTotal();
				cantidad += pedido.getPedidos().size();
			} else {
				ReporteVentasPorDia report = new ReporteVentasPorDia(momento, cantidad, montototal);
				reporte.add(report);
				cantidad = pedido.getPedidos().size();
				montototal = pedido.getPrecioTotal();
				momento = aux;
				c.setTime(momento);
			}

		}
		if (momento != null) {
			ReporteVentasPorDia report = new ReporteVentasPorDia(momento, cantidad, montototal);
			reporte.add(report);
		}

		return reporte;

	}

	/**
	 * Permite definir si dos fechas son las mismas
	 * 
	 * @param c  un objeto Calendar
	 * @param c1 un objeto Calendar
	 * @return retorna true si las dos fechas son las mismas o false si no lo son
	 */
	private boolean areEqualDays(Calendar c, Calendar c1) {
		boolean sameDay = c.get(Calendar.DAY_OF_YEAR) == c1.get(Calendar.DAY_OF_YEAR)
				&& c.get(Calendar.YEAR) == c1.get(Calendar.YEAR);
		return sameDay;
	}
}
