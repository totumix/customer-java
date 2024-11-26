package com.customer.customer.repository;

import com.customer.customer.model.Cliente;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class ClienteRepository {

    private static final String CLIENTES_FILE_PATH = "src/main/resources/data/clients.json";
    private ObjectMapper objectMapper;

    public ClienteRepository() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    // Lee los clientes desde el archivo JSON
    public List<Cliente> findAll() {
        try {
            // Si el archivo no existe, retornamos una lista vacía
            File file = new File(CLIENTES_FILE_PATH);
            if (!file.exists()) {
                return List.of();
            }

            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Cliente.class));
        } catch (IOException e) {
            e.printStackTrace();
            return List.of(); // Retorna una lista vacía en caso de error
        }
    }

    // Guarda los clientes en el archivo JSON
    public void save(Cliente cliente) {
        try {
            List<Cliente> clientes = findAll();
            clientes.add(cliente);
            objectMapper.writeValue(new File(CLIENTES_FILE_PATH), clientes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
