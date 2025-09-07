package com.cuentasservice.controller;

import com.cuentasservice.dto.MovimientoDto;
import com.cuentasservice.exception.NotFoundException;
import com.cuentasservice.model.Cuenta;
import com.cuentasservice.model.Movimiento;
import com.cuentasservice.repository.CuentaRepository;
import com.cuentasservice.repository.MovimientoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;

    public MovimientoController(MovimientoRepository movimientoRepository, CuentaRepository cuentaRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    // Registrar movimiento
    @PostMapping
    public ResponseEntity<Movimiento> registrarMovimiento(@RequestBody MovimientoDto dto) {
        Cuenta cuenta = cuentaRepository.findById(dto.getIdCuenta())
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada con id " + dto.getIdCuenta()));

        // Validar saldo
// Validar saldo
        BigDecimal nuevoSaldo = cuenta.getSaldoInicial().add(dto.getValor());

        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        Movimiento movimiento = new Movimiento();
        movimiento.setTipoMovimiento(dto.getTipoMovimiento());
        movimiento.setValor(dto.getValor()); // dto ya devuelve BigDecimal
        movimiento.setSaldo(nuevoSaldo);     // también BigDecimal
        movimiento.setCuenta(cuenta);

        return ResponseEntity.ok(movimientoRepository.save(movimiento));

    }
    // Listar movimientos de una cuenta
    @GetMapping("/cuenta/{idCuenta}")
    public ResponseEntity<List<Movimiento>> listarPorCuenta(@PathVariable Integer idCuenta) {
        Cuenta cuenta = cuentaRepository.findById(Long.valueOf(idCuenta))
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada con id " + idCuenta));
        return ResponseEntity.ok(movimientoRepository.findByCuenta(cuenta));
    }
}
