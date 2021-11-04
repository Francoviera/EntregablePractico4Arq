package entregable4.despensa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import entregable4.despensa.entities.ProductoStock;

public interface ProductoStockRepository extends JpaRepository<ProductoStock, Integer> {
	@Query("SELECT p FROM ProductoStock p WHERE p.producto.id=:id")
	ProductoStock findStockByIdProducto(int id);
}
