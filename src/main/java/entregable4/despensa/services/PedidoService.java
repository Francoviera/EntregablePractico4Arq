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

	/**
	 * Permite obtener un Pedido mediante su id
	 * 
	 * @param id es el identificador unico de cada Pedido, es un entero
	 * @return retorna un Optional de Pedido
	 */
	public Optional<Pedido> getPedido(int id) {
		return this.pedidos.findById(id);
	}

	/**
	 * Permite obtener todos los pedidos de un determinado cliente
	 * 
	 * @param idCliente un entero, identificador unico de cada cliente
	 * @param date      una fecha en formato Date
	 * @return retorna una lista de Pedido
	 */
	public List<Pedido> getPedidosByCliente(int idCliente, Date date) {
		return this.pedidos.findPedidoOfCliente(idCliente, date);
	}

	/**
	 * Permite obtener la lista de todos los pedidos
	 * 
	 * @return retorna una lista de Pedido
	 */
	public List<Pedido> getPedidos() {
		return this.pedidos.findAll();
	}

	/**
	 * Permite eliminar un pedido
	 * 
	 * @param p una entidad de tipo Pedido
	 */
	public void deletePedido(Pedido p) {
		this.pedidos.delete(p);
	}

	/**
	 * Permite agregar un pedido siempre y cuando el cliente no supere el limite de
	 * compra de 3 productos por pedido por dia
	 * 
	 * @param p una entidad Pedido
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
		if (listaNueva.size() != 0) {
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
	 * @param p          la entidad Pedido en cuestion
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
						// ACTUALIZAR
						int id = productoStock.getId();
						int stockNuevo = stockDisponible - cantidadPedir;

						precioTotal += cantidadPedir * precio;
						stock.updateStock(id, stockNuevo);
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
	 * @param p          una entidad Pedido
	 * @param date       la fecha en la que se realizo el pedido
	 * @param listaNueva la lista de los itemspedidos del pedido en cuestión
	 * @return retorna true si la operación fue exitosa o false si no lo fue
	 */
	private boolean chequearCantidad(Pedido p, Date date, List<ItemPedido> listaNueva) {
		int clienteId = p.getCliente().getId();
		List<Pedido> lista = this.getPedidosByCliente(clienteId, date);

		if (!chequearCantidadPedidoNuevo(listaNueva)) {
			return false;
		} else {
			if (lista.size() != 0) {
				chequearCantidadPedidoContraPedidos(lista, listaNueva);
			}
		}

		return true;
	}

	/**
	 * Chequea que la cantidad comprada previamente mas la cantidad que se quiere
	 * adquirir sea menor o igual a 3
	 * 
	 * @param lista      la lista de pedidos del cliente en la fecha que se quiere
	 *                   hacer el nuevo pedido
	 * @param listaNueva la lista de items pedidos en el pedido nuevo
	 * @return retorna true si la compra se puede hacer o false si la cantidad es
	 *         excedida en algun item del pedido
	 */
	private boolean chequearCantidadPedidoContraPedidos(List<Pedido> lista, List<ItemPedido> listaNueva) {
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
	 * Chequea que ningun itempedido de la lista de itemspedidos tenga un numero
	 * mayor a 3
	 * 
	 * @param listaNueva la lista de items pedidos a verificar
	 * @return retorna true si ningun producto supera las 3 unidades o false si las
	 *         supera
	 */
	private boolean chequearCantidadPedidoNuevo(List<ItemPedido> listaNueva) {
		for (ItemPedido nueva : listaNueva) {
			int contador = nueva.getCantidad();
			if (contador > 3) {
//				rechazar pedido;
				return false;
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
