package com.example.clientesservice.service;

import com.clienteservice.model.Cliente;
import com.clienteservice.model.Persona;
import com.clienteservice.repository.ClienteRepository;
import com.clienteservice.repository.PersonaRepository;
import com.clienteservice.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PersonaRepository personaRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Persona persona = new Persona();
        persona.setIdPersona(1);
        persona.setNombre("Carlos Pérez");
        persona.setIdentificacion("1122334455");

        cliente = new Cliente();
        cliente.setIdCliente(1L);
        cliente.setPersona(persona);
        cliente.setContrasena("abc1234");
        cliente.setEstado(true);
    }

    @Test
    void testCrearCliente() {
        when(personaRepository.save(cliente.getPersona())).thenReturn(cliente.getPersona());
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente creado = clienteService.crear(cliente);

        assertNotNull(creado);
        assertEquals("Carlos Pérez", creado.getPersona().getNombre());
        verify(personaRepository, times(1)).save(cliente.getPersona());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void testObtenerPorId() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente encontrado = clienteService.obtenerPorId(1L);

        assertNotNull(encontrado);
        assertEquals(1, encontrado.getIdCliente());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerPorId_NotFound() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> clienteService.obtenerPorId(99L));

        assertEquals("Cliente no encontrado con id: 99", ex.getMessage());
    }

    @Test
    void testListarClientes() {
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente));

        List<Cliente> lista = clienteService.listar();

        assertEquals(1, lista.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void testActualizarCliente() {
        Cliente actualizado = new Cliente();
        Persona personaNueva = new Persona();
        personaNueva.setIdPersona(2);
        personaNueva.setNombre("Juan López");
        personaNueva.setIdentificacion("55667788");

        actualizado.setPersona(personaNueva);
        actualizado.setContrasena("xyz987");
        actualizado.setEstado(false);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(personaRepository.save(any())).thenReturn(personaNueva);
        when(clienteRepository.save(any())).thenReturn(actualizado);

        Cliente resultado = clienteService.actualizar(1L, actualizado);

        assertNotNull(resultado);
        assertEquals("Juan López", resultado.getPersona().getNombre());
        assertEquals("xyz987", resultado.getContrasena());
        assertFalse(resultado.getEstado());

        verify(clienteRepository, times(1)).findById(1L);
        verify(personaRepository, times(1)).save(personaNueva);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testEliminarCliente() {
        doNothing().when(clienteRepository).deleteById(1L);

        clienteService.eliminar(1L);

        verify(clienteRepository, times(1)).deleteById(1L);
    }
}