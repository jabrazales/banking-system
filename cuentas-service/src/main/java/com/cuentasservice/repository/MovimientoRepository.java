package com.cuentasservice.repository;

import com.cuentasservice.model.Movimiento;
import com.cuentasservice.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {

    List<Movimiento> findByCuenta(Cuenta cuenta);

    List<Movimiento> findByCuentaAndFechaBetween(Cuenta cuenta, LocalDateTime inicio, LocalDateTime fin);
}
