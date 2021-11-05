package entregable4.despensa.DTO;


public class ReporteCompras {
	
	private String nombreCliente;
	
	private String apellidoCliente;
		
	private double total;
	
	
	public ReporteCompras(String nombreCliente, String apellidoCliente, double total) {
		this.nombreCliente= nombreCliente;
		this.apellidoCliente= apellidoCliente;
		this.total= total;
	}


	public String getNombreCliente() {
		return nombreCliente;
	}


	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}


	public String getApellidoCliente() {
		return apellidoCliente;
	}


	public void setApellidoCliente(String apellidoCliente) {
		this.apellidoCliente = apellidoCliente;
	}


	public double getTotal() {
		return total;
	}


	public void setTotal(double total) {
		this.total = total;
	}
	

}
