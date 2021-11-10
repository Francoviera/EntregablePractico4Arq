package entregable4.despensa.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import entregable4.despensa.DTO.ReporteVentasPorDia;
import entregable4.despensa.entities.Pedido;
import entregable4.despensa.services.PedidoService;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
@RestController
@RequestMapping("/pedidos") // la convencion es plural
public class PedidoController {
	@Autowired
	private PedidoService pedidoService;

	@GetMapping("")
	public List<Pedido> getAllPedidos() {
		return this.pedidoService.getPedidos();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pedido> getPedido(@PathVariable("id") int id) {
		Optional<Pedido> pedido = pedidoService.getPedido(id);
		if (pedido.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(pedido.get(), HttpStatus.OK);
		}
	}

	@PostMapping("")
	public ResponseEntity<Pedido> addPedido(@RequestBody Pedido p) {
		boolean ok = pedidoService.addPedido(p);
		if (!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

		} else {
			return new ResponseEntity<>(p, HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Pedido> deletePedido(@PathVariable("id") int id) {
		Optional<Pedido> pedido = pedidoService.getPedido(id);
		if (!pedido.isEmpty()) {
			pedidoService.deletePedido(pedido.get());
			return new ResponseEntity<Pedido>(pedido.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Pedido>(HttpStatus.NOT_FOUND);
		}
	}

	
	@GetMapping("/ventasdiarias")
	public ResponseEntity<ArrayList<ReporteVentasPorDia>> getSalesByDay()
			throws ParseException {

		ArrayList<ReporteVentasPorDia> reportes = pedidoService.getSalesByDay();
		if (reportes.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(reportes, HttpStatus.OK);
		}
	}
}
