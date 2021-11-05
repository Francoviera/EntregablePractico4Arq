package entregable4.despensa.DTO;

import java.util.Date;

public class ReporteVentasPorDia {
	
	private Date date;
	
	private String nombreCliente;
	
	private double total;
	
	public ReporteVentasPorDia(Date date, String nombreCliente, double total) {
		this.date= date;
		this.nombreCliente= nombreCliente;
		this.total= total;
	}

}
