package com.cuentasservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CuentaDto {
    private Long idCliente;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private Boolean estado;

}
