package entregable4.despensa.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pedido {
	private int id;
	private Cliente cliente;
	private Timestamp momentoCompra;
	private ArrayList<Producto> productos;
	private Map<Cliente, List<Producto>> productosMap;
	
	
	
}
