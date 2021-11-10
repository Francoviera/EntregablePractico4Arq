package entregable4.despensa.DTO;

import java.util.Date;

/**
 * DTO Creado para devolver el reporte de ventas por dia
 * Punto 4
 * @author Grupo 15
 */
public class ReporteVentasPorDia {
	
	private Date date;

	private int cantidadVentas;
	
	private double total;
	
	public ReporteVentasPorDia(Date date, int cantidadVentas, double total) {
		this.date= date;
		this.cantidadVentas=cantidadVentas;
		this.total= total;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getCantidadVentas() {
		return cantidadVentas;
	}

	public void setCantidadVentas(int cantidadVentas) {
		this.cantidadVentas = cantidadVentas;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
}
