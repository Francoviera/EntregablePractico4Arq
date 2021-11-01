package entregable4.despensa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entregable4.despensa.entities.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
