package com.example.clientesservice.service;

import com.clienteservice.model.Cliente;
import com.clienteservice.model.Persona;
import com.clienteservice.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = com.clienteservice.ClientesServiceApplication.class)
class ClienteRepositoryIntegrationTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void guardarYRecuperarCliente() {
        Persona persona = new Persona();
        persona.setNombre("Juan");
        persona.setIdentificacion("99887766");

        Cliente cliente = new Cliente();
        cliente.setPersona(persona);
        cliente.setContrasena("pass123");
        cliente.setEstado(true);

        Cliente guardado = clienteRepository.save(cliente);
        assertThat(guardado.getIdCliente()).isNotNull();

        Cliente encontrado = clienteRepository.findById(guardado.getIdCliente()).orElse(null);
        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getPersona().getNombre()).isEqualTo("Juan");
    }
}

