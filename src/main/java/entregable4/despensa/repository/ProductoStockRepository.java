package entregable4.despensa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entregable4.despensa.entities.ProductoStock;

public interface ProductoStockRepository extends JpaRepository<ProductoStock, Integer> {

}
