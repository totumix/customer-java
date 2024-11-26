package com.customer.customer.dto;

public class ClienteEstadisticas {

    private long cantidadClientes;
    private double promedioEdad;

    public ClienteEstadisticas(long cantidadClientes, double promedioEdad) {
        this.cantidadClientes = cantidadClientes;
        this.promedioEdad = promedioEdad;
    }

}
