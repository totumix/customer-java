package com.customer.customer.service;

import com.customer.customer.dto.ClienteEstadisticas;
import com.customer.customer.model.Cliente;
import com.customer.customer.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository = new ClienteRepository();

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public void saveCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public List<Cliente> getClientesOrdenadosPorNombre() {
        return clienteRepository.findAll().stream()
                .sorted((c1, c2) -> c1.getNombreCompleto().compareTo(c2.getNombreCompleto()))
                .toList();
    }

    public List<Cliente> getClientesOrdenadosPorEdad() {
        return clienteRepository.findAll().stream()
                .sorted((c1, c2) -> c1.getFechaNacimiento().compareTo(c2.getFechaNacimiento()))
                .toList();
    }

    public ClienteEstadisticas getCantidadClientesYPromedioEdad() {
        List<Cliente> clientes = clienteRepository.findAll();

        // Contamos la cantidad de clientes
        long cantidadClientes = clientes.size();

        // Calculamos el promedio de edad
        double promedioEdad = clientes.stream()
                .mapToInt(c -> calcularEdad(c.getFechaNacimiento()))
                .average()
                .orElse(0);

        return new ClienteEstadisticas(cantidadClientes, promedioEdad);
    }

    // Calcula la edad en años a partir de la fecha de nacimiento
    private int calcularEdad(LocalDate fechaNacimiento) {
        LocalDate today = LocalDate.now();
        return Period.between(fechaNacimiento, today).getYears();
    }
}
