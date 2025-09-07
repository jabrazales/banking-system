package com.clienteservice.service;

import com.clienteservice.config.RabbitMQConfig;
import com.clienteservice.model.Cliente;
import com.clienteservice.repository.ClienteRepository;
import com.clienteservice.repository.PersonaRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;   // 👈 nuevo import
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PersonaRepository personaRepository;
    private final RabbitTemplate rabbitTemplate;   // 👈 inyección de RabbitMQ

    public ClienteService(ClienteRepository clienteRepository,
                          PersonaRepository personaRepository,
                          RabbitTemplate rabbitTemplate) {
        this.clienteRepository = clienteRepository;
        this.personaRepository = personaRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    // Crear cliente
    @Transactional
    public Cliente crear(Cliente cliente) {
        personaRepository.save(cliente.getPersona());
        Cliente nuevo = clienteRepository.save(cliente);

        // 👇 Enviar evento a RabbitMQ con el id del nuevo cliente
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                nuevo.getIdCliente()
        );

        return nuevo;
    }

    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
    }

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @Transactional
    public Cliente actualizar(Long id, Cliente cliente) {
        Cliente existente = obtenerPorId(id);

        existente.setPersona(cliente.getPersona());
        existente.setContrasena(cliente.getContrasena());
        existente.setEstado(cliente.getEstado());

        personaRepository.save(existente.getPersona());
        return clienteRepository.save(existente);
    }

    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}
