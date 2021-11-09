package entregable4.despensa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entregable4.despensa.entities.ProductoStock;
import entregable4.despensa.services.ProductoStockService;

@RestController
@RequestMapping("/productostock")
public class ProductoStockController {

	@Autowired
	private ProductoStockService productoStockService;

	@GetMapping("")
	public List<ProductoStock> getAll() {
		return this.productoStockService.getProductoStock();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductoStock> getProductoStock(@PathVariable("id") int id) {
		Optional<ProductoStock> Producto = productoStockService.getProductoStock(id);
		if (Producto.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(Producto.get(), HttpStatus.OK);
		}
	}

	@PostMapping("")
	public ResponseEntity<ProductoStock> addProductoStock(@RequestBody ProductoStock p) {
		boolean ok = productoStockService.addProductoStock(p);
		if (!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

		} else {
			return new ResponseEntity<>(p, HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ProductoStock> deleteProductoStock(@PathVariable("id") int id) {
		Optional<ProductoStock> ProductoStock = productoStockService.getProductoStock(id);
		if (!ProductoStock.isEmpty()) {
			productoStockService.deleteProductoStock(ProductoStock.get());
			return new ResponseEntity<ProductoStock>(ProductoStock.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<ProductoStock>(HttpStatus.NOT_FOUND);
		}
	}
}
