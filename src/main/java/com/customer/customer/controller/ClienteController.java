package com.customer.customer.controller;
import com.customer.customer.dto.ClienteEstadisticas;
import com.customer.customer.dto.EdadResponse;
import com.customer.customer.model.Cliente;
import com.customer.customer.service.ClienteService;
import com.customer.customer.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/generar-token")
    public Map<String, String> generarToken(@RequestParam String nombre, @RequestParam String correo) {
        return jwtService.generateToken(nombre, correo);
    }

    @PostMapping
    public ResponseEntity<String> createCliente(@RequestBody Cliente cliente) {
        clienteService.saveCliente(cliente);
        return new ResponseEntity<>("Cliente creado exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/todos")
    public List<Cliente> getAllClientes() {
        return clienteService.getAllClientes();
    }

    @GetMapping("/ordenados-nombre")
    public List<Cliente> getClientesOrdenadosPorNombre() {
        return clienteService.getClientesOrdenadosPorNombre();
    }

    @GetMapping("/ordenados-edad")
    public List<Cliente> getClientesOrdenadosPorEdad() {
        return clienteService.getClientesOrdenadosPorEdad();
    }

    @GetMapping("/estadisticas")
    public ClienteEstadisticas getEstadisticasClientes() {
        return clienteService.getCantidadClientesYPromedioEdad();
    }

    @GetMapping("/edad")
    public EdadResponse getEdadCliente(@RequestParam String correo) {
        int edad = clienteService.obtenerEdadPorCorreo(correo);
        System.out.println(edad);
        return new EdadResponse(edad);
    }
}
