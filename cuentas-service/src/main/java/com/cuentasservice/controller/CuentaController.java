package com.cuentasservice.controller;

import com.cuentasservice.dto.CuentaDto;
import com.cuentasservice.exception.NotFoundException;
import com.cuentasservice.model.Cuenta;
import com.cuentasservice.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaRepository cuentaRepository;

    @PostMapping
    public ResponseEntity<Cuenta> crearCuenta(@RequestBody CuentaDto dto) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setSaldoInicial(dto.getSaldoInicial()); // BigDecimal directo
        cuenta.setEstado(dto.getEstado());
        cuenta.setIdCliente(dto.getIdCliente());       // Long
        return ResponseEntity.ok(cuentaRepository.save(cuenta));
    }

    @GetMapping
    public ResponseEntity<List<Cuenta>> listarCuentas() {
        return ResponseEntity.ok(cuentaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> obtenerCuenta(@PathVariable Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada con id " + id));
        return ResponseEntity.ok(cuenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable Long id, @RequestBody CuentaDto dto) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada con id " + id));

        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setSaldoInicial(dto.getSaldoInicial()); // BigDecimal directo
        cuenta.setEstado(dto.getEstado());

        return ResponseEntity.ok(cuentaRepository.save(cuenta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada con id " + id));
        cuentaRepository.delete(cuenta);
        return ResponseEntity.noContent().build();
    }
}
