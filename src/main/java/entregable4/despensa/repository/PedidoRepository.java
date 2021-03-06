package entregable4.despensa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import entregable4.despensa.entities.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

	@Query("SELECT p FROM Pedido p WHERE p.cliente.idCliente=:id"
			+ " AND YEAR(:date)= YEAR(p.momentoCompra) AND MONTH(:date)= MONTH(p.momentoCompra) "
			+ "AND DAY(:date)= DAY(p.momentoCompra)")
	List<Pedido> findPedidoOfCliente(int id, Date date);

	@Query("SELECT p FROM Pedido p GROUP BY(p.idPedido, YEAR(p.momentoCompra), MONTH(p.momentoCompra), DAY(p.momentoCompra)) ORDER BY (YEAR(p.momentoCompra), MONTH(p.momentoCompra), DAY(p.momentoCompra))")
	List<Pedido> findPedidosByDay();

}
