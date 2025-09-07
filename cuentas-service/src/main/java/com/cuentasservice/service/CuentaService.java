package com.cuentasservice.service;

import com.cuentasservice.model.Cuenta;
import com.cuentasservice.repository.CuentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {

    private final CuentaRepository repo;

    public CuentaService(CuentaRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Cuenta crearCuenta(Cuenta cuenta) {
        return repo.save(cuenta);
    }

    public List<Cuenta> listarCuentas() {
        return repo.findAll();
    }

    public Optional<Cuenta> obtenerCuentaPorId(Integer id) {
        return repo.findById(Long.valueOf(id));
    }

    public Optional<Cuenta> obtenerPorNumeroCuenta(String numeroCuenta) {
        return repo.findByNumeroCuenta(numeroCuenta);
    }

    @Transactional
    public Cuenta actualizarCuenta(Integer id, Cuenta cuenta) {
        return repo.findById(Long.valueOf(id)).map(ex -> {
            ex.setNumeroCuenta(cuenta.getNumeroCuenta());
            ex.setTipoCuenta(cuenta.getTipoCuenta());
            ex.setSaldoInicial(cuenta.getSaldoInicial());
            ex.setEstado(cuenta.getEstado());
            ex.setIdCliente(cuenta.getIdCliente());
            return repo.save(ex);
        }).orElseThrow(() -> new RuntimeException("Cuenta no encontrada: " + id));
    }
}
