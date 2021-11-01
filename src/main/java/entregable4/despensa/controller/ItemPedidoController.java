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

import entregable4.despensa.entities.ItemPedido;
import entregable4.despensa.services.ItemPedidoService;

@RestController
@RequestMapping("/itempedido")
public class ItemPedidoController {

	@Autowired
	private ItemPedidoService itemPedidoService;

	@GetMapping("")
	public List<ItemPedido> getAll() {
		return this.itemPedidoService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ItemPedido> getItemPedido(@PathVariable("id") int id) {
		Optional<ItemPedido> itemPedido = itemPedidoService.getItemPedido(id);
		if (itemPedido.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(itemPedido.get(), HttpStatus.OK);
		}
	}

	@PostMapping("")
	public ResponseEntity<ItemPedido> addItemPedido(@RequestBody ItemPedido p) {
		boolean ok = itemPedidoService.addItemPedido(p);
		if (!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

		} else {
			return new ResponseEntity<>(p, HttpStatus.OK);
		}
	}

	@DeleteMapping("")
	public ResponseEntity<ItemPedido> deleteItemPedido(@RequestBody int id) {
		Optional<ItemPedido> itemPedido = itemPedidoService.getItemPedido(id);
		if (itemPedido.isEmpty()) {
			itemPedidoService.deleteItemPedido(itemPedido.get());
			return new ResponseEntity<>(itemPedido.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
