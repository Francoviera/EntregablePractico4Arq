package entregable4.despensa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import entregable4.despensa.DTO.ReporteCompras;
import entregable4.despensa.entities.Cliente;
import entregable4.despensa.responses.responseDate;
import entregable4.despensa.services.ClienteService;

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
		Optional<Cliente> cliente =clienteService.getCliente(id);
		if(cliente.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(cliente.get(),HttpStatus.OK);
		}
	}
	
//	@PostMapping("/ventasPorDia")
//	public ResponseEntity<ArrayList<ReporteCompras>> getSalesByDay(@RequestBody responseDate dateString) throws ParseException{
//		SimpleDateFormat formato = new SimpleDateFormat("yyyy/mm/dd"); 
//		Date date = formato.parse(dateString.getDate());
//		ArrayList<ReporteCompras> reportes =clienteService.getSalesByDay(date);
//		if(reportes.isEmpty()) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}else {
//			return new ResponseEntity<>(reportes,HttpStatus.OK);
//		}
//	}
	
	@GetMapping("/ventasPorDia/{date}")
	public ResponseEntity<ArrayList<ReporteCompras>> getSalesByDay(@PathVariable("date")String date) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd"); 
//        Date date = format.parse("20-08-1981"); 
		System.out.println(format.parse("2021-11-04"));
        Date finalDate = format.parse("2021-11-04"); 
        
		ArrayList<ReporteCompras> reportes =clienteService.getSalesByDay(finalDate);
		if(reportes.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(reportes,HttpStatus.OK);
		}
	}
	
	@GetMapping("/reporteDeVentas")
	public ResponseEntity<ArrayList<ReporteCompras>> getSalesByClient(){
		ArrayList<ReporteCompras> reportes =clienteService.getTotalOfPedidosByCliente();
		if(reportes.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(reportes,HttpStatus.OK);
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
		Optional<Cliente> cliente=  clienteService.getCliente(id);
		if(cliente.isEmpty()) {
			clienteService.deleteCliente(cliente.get());
			return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(cliente.get(),HttpStatus.NOT_FOUND);
		}
	}
	
	//@PostMapping("/buyProduct")

}