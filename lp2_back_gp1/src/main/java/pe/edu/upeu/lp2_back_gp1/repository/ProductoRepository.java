package pe.edu.upeu.lp2_back_gp1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.lp2_back_gp1.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}

