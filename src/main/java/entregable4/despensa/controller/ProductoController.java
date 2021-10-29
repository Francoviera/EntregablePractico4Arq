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

import edu.isistan.bookstore.entities.Libro;
import entregable4.despensa.entities.Cliente;
import entregable4.despensa.entities.Producto;
import entregable4.despensa.services.ProductoService;

@RestController
@RequestMapping("/producto")
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	

	@GetMapping("")
	public List<Producto>getAll() {
		return this.productoService.getProductos();
	}
	@GetMapping("/{id}")
	public ResponseEntity<Producto> getProducto(@PathVariable("id")int id){
		Optional<Producto> Producto =productoService.getProducto(id);
		if(Producto.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(Producto.get(),HttpStatus.OK);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<Producto> addProducto(@RequestBody Producto p){
		boolean ok=  productoService.addProducto(p);
		if(!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

		}else {
			return new ResponseEntity<>(p,HttpStatus.OK);
		}
	}
	
	@DeleteMapping("")
	public ResponseEntity<Producto> deleteCliente(@RequestBody int id){
		Optional<Producto> Producto=  productoService.getProducto(id);
		if(Producto.isEmpty()) {
			productoService.deleteProducto(Producto.get());
			return new ResponseEntity<>(Producto.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
