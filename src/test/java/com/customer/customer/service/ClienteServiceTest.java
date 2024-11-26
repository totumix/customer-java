package com.customer.customer.service;

import com.customer.customer.model.Cliente;
import com.customer.customer.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        // No es necesario inicializar los mocks aquí, @MockBean se encarga
    }

    @Test
    void testCalcularEdad_validaFechaNacimiento() {
        // Datos de prueba
        Cliente cliente = new Cliente();
        cliente.setFechaNacimiento(LocalDate.of(1990, 5, 15));

        // Ejecutar el método
        int edad = clienteService.calcularEdad(cliente);

        // Verificar el resultado
        assertEquals(34, edad); // Ajusta según la fecha actual
    }

    @Test
    void testCalcularEdad_fechaNacimientoNula() {
        // Datos de prueba con fecha nula
        Cliente cliente = new Cliente();
        cliente.setFechaNacimiento(null);

        // Ejecutar el método
        int edad = clienteService.calcularEdad(cliente);

        // Verificar el resultado
        assertEquals(0, edad);
    }

    @Test
    void testObtenerEdadPorCorreo_clienteEncontrado() {
        // Datos de prueba
        Cliente cliente = new Cliente();
        cliente.setCorreoElectronico("totucr95@gmail.com");
        cliente.setFechaNacimiento(LocalDate.of(1995, 5, 15));

        // Mockear el repositorio para devolver el cliente
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        // Ejecutar el método
        int edad = clienteService.obtenerEdadPorCorreo("totucr95@gmail.com");

        // Verificar el resultado
        assertEquals(29, edad); // Ajusta según la fecha actual
    }

    @Test
    void testObtenerEdadPorCorreo_clienteNoEncontrado() {
        // Mockear el repositorio para que no devuelva clientes
        when(clienteRepository.findAll()).thenReturn(List.of());

        // Ejecutar el método y verificar que se lanza una excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.obtenerEdadPorCorreo("juan@example.com");
        });

        // Verificar el mensaje de la excepción
        assertEquals("Cliente no encontrado", exception.getMessage());
    }
}

