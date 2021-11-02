package entregable4.despensa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import entregable4.despensa.entities.Cliente;
import entregable4.despensa.entities.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
