package entregable4.despensa.services;

import java.sql.Date;
import java.sql.Timestamp;
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
		this.pedidos.delete(p);
	}

	public boolean addPedido(Pedido p) {
		// ACA LE DEFINIMOS LA FECHA
		if(p.getMomentoCompra()==null) {
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
		}else {
			return false;
		}
		
		
		return this.pedidos.save(p) != null;
	}

	private Date definirHora() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Date date = new Date(ts.getTime());
		return date;
	}

	private boolean venderPedido(Pedido p, List<ItemPedido> listaNueva) {
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
		return true;
	}

	private boolean chequearCantidad(Pedido p, Date date, List<ItemPedido> listaNueva) {
		List<Pedido> lista = this.getPedidosByCliente(p.getCliente().getId(), date);
		for (Pedido pedido : lista) {
			List<ItemPedido> listaAux = pedido.getPedidos();
			for (ItemPedido item : listaAux) {
				if (listaNueva.contains(item)) {
					int contador = item.getCantidad() + listaNueva.get(listaNueva.indexOf(item)).getCantidad();
					if (contador > 3) {
//						rechazar pedido;
						return false;
					}
				}
			}
		}
		return true;
	}
	
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
				// puedo sumar
				montototal += pedido.getPrecioTotal();
				cantidad += pedido.getPedidos().size();
				momento = aux;
				c.setTime(momento);
			} else if (c.get(Calendar.DATE) == c1.get(Calendar.DATE) && c.get(Calendar.MONTH) == c1.get(Calendar.MONTH)
					&& c.get(Calendar.YEAR) == c1.get(Calendar.YEAR)) {
				montototal += pedido.getPrecioTotal();
				cantidad += pedido.getPedidos().size();
			} else {
				ReporteVentasPorDia report = new ReporteVentasPorDia(momento, cantidad, montototal);
				reporte.add(report);
				cantidad = pedido.getPedidos().size();
				montototal = pedido.getPrecioTotal();
				momento = aux;
			}

		}
		if (momento != null) {
			ReporteVentasPorDia report = new ReporteVentasPorDia(momento, cantidad, montototal);
			reporte.add(report);
		}

		return reporte;

	}

}
