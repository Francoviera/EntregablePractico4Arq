package entregable4.despensa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entregable4.despensa.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}
