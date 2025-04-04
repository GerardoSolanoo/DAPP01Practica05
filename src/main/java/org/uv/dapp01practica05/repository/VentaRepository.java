package org.uv.dapp01practica05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uv.dapp01practica05.entity.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}
