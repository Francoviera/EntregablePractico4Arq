package entregable4.despensa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import entregable4.despensa.entities.ProductoStock;
import entregable4.despensa.services.ProductoStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
@RestController
@RequestMapping("/productosstock") // la convencion es plural
@Api(value = "ProductoStockController")
public class ProductoStockController {

	@Autowired
	private ProductoStockService productoStockService;

	@GetMapping("")
	@ApiOperation(value = "Obtener una lista de todos los productos que hay en stock")
	public List<ProductoStock> getAllProductosStock() {
		return this.productoStockService.getProductoStock();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Obtiene un producto del stock por su identificador único")
	public ResponseEntity<ProductoStock> getProductoStock(@PathVariable("id") int id) {
		Optional<ProductoStock> Producto = productoStockService.getProductoStock(id);
		if (!Producto.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(Producto.get(), HttpStatus.OK);
		}
	}

	@PostMapping("")
	@ApiOperation(value = "Agrega un producto al stock")
	public ResponseEntity<ProductoStock> addProductoStock(@RequestBody ProductoStock p) {
		boolean ok = productoStockService.addProductoStock(p);
		if (!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

		} else {
			return new ResponseEntity<>(p, HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Elimina un producto del stock por su identificador único")
	public ResponseEntity<ProductoStock> deleteProductoStock(@PathVariable("id") int id) {
		Optional<ProductoStock> ProductoStock = productoStockService.getProductoStock(id);
		if (ProductoStock.isPresent()) {
			productoStockService.deleteProductoStock(ProductoStock.get());
			return new ResponseEntity<ProductoStock>(ProductoStock.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<ProductoStock>(HttpStatus.NOT_FOUND);
		}
	}
}
