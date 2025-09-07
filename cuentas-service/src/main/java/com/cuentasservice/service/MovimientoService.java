package com.cuentasservice.service;

import com.cuentasservice.model.Movimiento;
import com.cuentasservice.model.Cuenta;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoService {
    Movimiento registrarMovimiento(Movimiento movimiento);
    List<Movimiento> listarMovimientosPorCuenta(Cuenta cuenta);
    List<Movimiento> listarMovimientosPorCuentaYRango(Cuenta cuenta, LocalDateTime inicio, LocalDateTime fin);
}
