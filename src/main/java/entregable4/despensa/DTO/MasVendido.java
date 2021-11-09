package entregable4.despensa.DTO;


public class MasVendido {

	
	private String nombreProducto;
	
	private String marca;
	
	private int cantidad;

	public MasVendido(String nombreProducto, String marca, int cantidad) {
		super();
		this.nombreProducto = nombreProducto;
		this.marca = marca;
		this.cantidad = cantidad;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	

	
	
}
