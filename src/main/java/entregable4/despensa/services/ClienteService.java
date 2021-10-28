package entregable4.despensa.services;

import java.util.List;
import java.util.Optional;

import entregable4.despensa.entities.Cliente;
import entregable4.despensa.repository.ClienteRepository;

public class ClienteService {

	private ClienteRepository clientes;

	public Optional<Cliente> getCliente(int id) {
		return this.clientes.findById(id);
	}

	public List<Cliente> getAll() {
		return this.clientes.findAll(); // pasar a paginado
	}
}
