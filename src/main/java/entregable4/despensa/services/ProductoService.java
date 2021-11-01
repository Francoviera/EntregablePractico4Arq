package entregable4.despensa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entregable4.despensa.entities.Producto;
import entregable4.despensa.repository.ProductoRepository;

@Service
public class ProductoService {
	
	@Autowired
	private ProductoRepository productos;

	public Optional<Producto> getProducto(int id) {
		return this.productos.findById(id);
	}

	public List<Producto> getProductos() {
		return this.productos.findAll();
	}

	public boolean addProducto(Producto p) {
		return this.productos.save(p) != null;
	}

	public void deleteProducto(Producto p) {
		this.productos.delete(p);
	}

}
