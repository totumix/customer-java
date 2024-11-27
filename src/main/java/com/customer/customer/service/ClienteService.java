package com.customer.customer.service;

import com.customer.customer.dto.ClienteEstadisticas;
import com.customer.customer.model.Cliente;
import com.customer.customer.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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

        long cantidadClientes = clientes.size();

        double promedioEdad = clientes.stream()
                .mapToInt(this::calcularEdad)
                .average()
                .orElse(0);

        return new ClienteEstadisticas(cantidadClientes, promedioEdad);
    }


    public int calcularEdad(Cliente cliente) {
        System.out.println(cliente);
        if (cliente.getFechaNacimiento() != null) {
            LocalDate today = LocalDate.now();
            System.out.println(today);
            return Period.between(cliente.getFechaNacimiento(), today).getYears();
        }
        return 0;
    }

    public int obtenerEdadPorCorreo(String correo) {

        List<Cliente> clientes = clienteRepository.findAll();

        Optional<Cliente> clienteOptional = clientes.stream()
                .filter(cliente -> cliente.getCorreoElectronico().equals(correo))
                .findFirst();

        System.out.println((clienteOptional));

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            Function<Cliente, Integer> obtenerEdad = this::calcularEdad;
            return obtenerEdad.apply(cliente);
        } else {
            throw new RuntimeException("Cliente no encontrado");
        }
    }
}
