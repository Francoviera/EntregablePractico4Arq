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

import entregable4.despensa.entities.Pedido;
import entregable4.despensa.services.PedidoService;
@RestController
@RequestMapping("/pedido")
public class PedidoController {
	@Autowired
	private PedidoService pedidoService;

	@GetMapping("")
	public List<Pedido> getAll() {
		return this.pedidoService.getPedidos();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pedido> getItemPedido(@PathVariable("id") int id) {
		Optional<Pedido> pedido = pedidoService.getPedido(id);
		if (pedido.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(pedido.get(), HttpStatus.OK);
		}
	}

	@PostMapping("")
	public ResponseEntity<Pedido> addItemPedido(@RequestBody Pedido p) {
		boolean ok = pedidoService.addPedido(p);
		if (!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

		} else {
			return new ResponseEntity<>(p, HttpStatus.OK);
		}
	}

	@DeleteMapping("")
	public ResponseEntity<Pedido> deleteItemPedido(@RequestBody int id) {
		Optional<Pedido> pedido = pedidoService.getPedido(id);
		if (pedido.isEmpty()) {
			pedidoService.deletePedido(pedido.get());
			return new ResponseEntity<>(pedido.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("cliente/{id}")
	public List<Pedido> pedidosByCliente(@PathVariable("id") int id){
		return pedidoService.getPedidosByCliente(id);
	}
}
