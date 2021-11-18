package entregable4.despensa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import entregable4.despensa.DTO.ReporteCompras;
import entregable4.despensa.entities.Cliente;
import entregable4.despensa.services.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
@RestController
@RequestMapping("/clientes") // la convencion es plural
@Api(value = "ClienteController")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping("")
	@ApiOperation(value = "Obtener una lista de todos los clientes")
	public List<Cliente> getAllClientes() {
		return this.clienteService.getAll();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Obtiene un cliente por su identificador único")
	public ResponseEntity<Cliente> getCliente(@PathVariable("id") int id) {
		Optional<Cliente> cliente = clienteService.getCliente(id);
		if (cliente.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
		}
	}

	@PostMapping("")
	@ApiOperation(value = "Agrega un cliente")
	public ResponseEntity<Cliente> addCliente(@RequestBody Cliente c) {
		boolean ok = clienteService.addCliente(c);
		if (!ok) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

		} else {
			return new ResponseEntity<>(c, HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Elimina un cliente por su identificador único")
	public ResponseEntity<Cliente> deleteCliente(@PathVariable("id") int id) {
		Optional<Cliente> cliente = clienteService.getCliente(id);
		if (!cliente.isEmpty()) {
			clienteService.deleteCliente(cliente.get());
			return new ResponseEntity<Cliente>(cliente.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Cliente>(cliente.get(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/reportedeventas")
	@ApiOperation(value = "Obtiene un reporte de las compras que han hecho todos los clientes")
	public ResponseEntity<ArrayList<ReporteCompras>> getSalesByClient() {
		// ESTE RESUELVE EL PUNTO 3, PARA CADA CLIENTE, NOMBRE Y TOTAL GASTADO
		ArrayList<ReporteCompras> reportes = clienteService.getTotalOfPedidosByCliente();
		if (reportes.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(reportes, HttpStatus.OK);
		}
	}
}