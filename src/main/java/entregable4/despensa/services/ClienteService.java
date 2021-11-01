package entregable4.despensa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entregable4.despensa.entities.Cliente;
import entregable4.despensa.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clientes;

	public Optional<Cliente> getCliente(int id) {
		return this.clientes.findById(id);
	}

	public List<Cliente> getAll() {
		return this.clientes.findAll(); // pasar a paginado
	}

	public Boolean addCliente(Cliente c) {
		return this.clientes.save(c) != null;
	}

	public void deleteCliente(Cliente c) {
		this.clientes.delete(c);
	}
}
