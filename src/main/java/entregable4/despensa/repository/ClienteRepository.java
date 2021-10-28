package entregable4.despensa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import entregable4.despensa.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
