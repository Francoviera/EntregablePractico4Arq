package entregable4.despensa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import entregable4.despensa.entities.ProductoStock;
import entregable4.despensa.repository.ProductoStockRepository;

@Service
public class ProductoStockService {

	@Autowired
	private ProductoStockRepository productoStock;

	public Optional<ProductoStock> getProductoStock(int id) {
		return this.productoStock.findById(id);
	}

	public List<ProductoStock> getProductoStock() {
		return this.productoStock.findAll();
	}

	public boolean addProductoStock(ProductoStock p) {
		return this.productoStock.save(p) != null;
	}

	public void deleteProductoStock(ProductoStock p) {
		this.productoStock.delete(p);
	}
}
