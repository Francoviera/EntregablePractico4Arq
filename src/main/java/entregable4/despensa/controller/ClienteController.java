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
import entregable4.despensa.services.ClienteService;
import net.bytebuddy.asm.Advice.Return;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	
	@GetMapping("")
	public List<Cliente>getAll() {
		return this.clienteService.getAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> getCliente(@PathVariable("id")int id){
		Optional<Cliente> Cliente =clienteService.getCliente(id);
		if(Cliente.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(Cliente.get(),HttpStatus.OK);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<Cliente> addCliente(@RequestBody Cliente c){
		boolean ok=  clienteService.addCliente(c);
		if(!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

		}else {
			return new ResponseEntity<>(c,HttpStatus.OK);
		}
	}
	
	@DeleteMapping("")
	public ResponseEntity<Cliente> deleteCliente(@RequestBody int id){
		Optional<Cliente> Cliente=  clienteService.getCliente(id);
		if(Cliente.isEmpty()) {
			clienteService.deleteCliente(Cliente.get());
			return new ResponseEntity<>(Cliente.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(Cliente.get(),HttpStatus.NOT_FOUND);
		}
	}
}
