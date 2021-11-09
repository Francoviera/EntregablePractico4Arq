package entregable4.despensa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import entregable4.despensa.entities.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

//	List<ItemPedido> findByCliente(Cliente cliente);

//	@Query(name = "select p, COUNT(*) from ItemPedido p where p.professional = ?1 group by p.producto.id order by DESC ")
//	Pedido getMostProductSale();

	// CREO QUE ESTO ANDA
//	List<ItemPedido> findOrderByPedidoByCount();

	@Query("SELECT i from ItemPedido i JOIN i.producto p GROUP BY(i.idItem, i.producto) ORDER BY SUM(i.cantidad) ASC")
	List<ItemPedido> obtenerMasVendido();

}
