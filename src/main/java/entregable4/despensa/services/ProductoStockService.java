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

	/**
	 * Permite obtener un ProductoStock mediante su id
	 * 
	 * @param id es el identificador unico de cada producto del stock, es un entero
	 * @return retorna un Optional de ProductoStock
	 */
	public Optional<ProductoStock> getProductoStock(int id) {
		return this.productoStock.findById(id);
	}

	/**
	 * Permite obtener la lista de todos los productos del stock
	 * 
	 * @return retorna una lista de ProductoStock
	 */
	public List<ProductoStock> getProductoStock() {
		return this.productoStock.findAll();
	}

	/**
	 * Permite agregar un producto al stock
	 * 
	 * @param p una entidad ProductoStock
	 * @return retorna verdadero si lo pudo agregar o falso si la operacion fallo
	 */
	public boolean addProductoStock(ProductoStock p) {
		return this.productoStock.save(p) != null;
	}

	/**
	 * Permite eliminar un producto del stock
	 * 
	 * @param p una entidad ProductoStock
	 */
	public void deleteProductoStock(ProductoStock p) {
		this.productoStock.delete(p);
	}

	/**
	 * Permite actualizar el stock de un producto
	 * 
	 * @param idProductoStock es el identificador unico del producto en stock, es un
	 *                        entero
	 * @param nuevoStock      es el nuevo stock a asignar
	 */
	public void updateProductoStock(int idProductoStock, int nuevoStock) {
		this.productoStock.updateStock(idProductoStock, nuevoStock);
	}
}
