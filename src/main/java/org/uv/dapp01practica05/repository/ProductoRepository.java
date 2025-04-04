package org.uv.dapp01practica05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uv.dapp01practica05.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
