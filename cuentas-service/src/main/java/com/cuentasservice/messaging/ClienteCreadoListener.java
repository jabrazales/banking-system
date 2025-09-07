package com.cuentasservice.messaging;

import com.cuentasservice.config.RabbitMQConfig;
import com.cuentasservice.model.Cuenta;
import com.cuentasservice.service.CuentaService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class ClienteCreadoListener {

    private final CuentaService cuentaService;

    public ClienteCreadoListener(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void onClienteCreado(Long idCliente) {
        // Genera número simple y crea cuenta automática
        String numero = "ACC-" + idCliente + "-" + (Instant.now().toEpochMilli() % 10000);

        Cuenta c = new Cuenta();
        c.setNumeroCuenta(numero);
        c.setTipoCuenta("Ahorros");
        c.setSaldoInicial(BigDecimal.ZERO);
        c.setEstado(true);
        c.setIdCliente(idCliente); // asegúrate que tu entidad Cuenta tenga este campo

        cuentaService.crearCuenta(c);
    }
}
