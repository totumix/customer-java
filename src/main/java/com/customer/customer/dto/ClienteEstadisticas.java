package com.customer.customer.dto;

public class ClienteEstadisticas {

    private long cantidadClientes;
    private double promedioEdad;

    public ClienteEstadisticas(long cantidadClientes, double promedioEdad) {
        this.cantidadClientes = cantidadClientes;
        this.promedioEdad = promedioEdad;
    }

    // Getters
    public long getCantidadClientes() {
        return cantidadClientes;
    }

    public double getPromedioEdad() {
        return promedioEdad;
    }

    // Setters
    public void setCantidadClientes(long cantidadClientes) {
        this.cantidadClientes = cantidadClientes;
    }

    public void setPromedioEdad(double promedioEdad) {
        this.promedioEdad = promedioEdad;
    }
}
