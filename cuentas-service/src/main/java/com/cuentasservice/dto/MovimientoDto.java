package com.cuentasservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MovimientoDto {
    private String tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldo;
    private Long idCuenta;
}
