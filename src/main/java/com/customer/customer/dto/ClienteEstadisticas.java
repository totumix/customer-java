package com.customer.customer.dto;

public class ClienteEstadisticas {

    private long cantidadClientes;
    private double promedioEdad;

    public ClienteEstadisticas(long cantidadClientes, double promedioEdad) {
        this.cantidadClientes = cantidadClientes;
        this.promedioEdad = promedioEdad;
    }

    public long getCantidadClientes() {
        return cantidadClientes;
    }

    public void setCantidadClientes(long cantidadClientes) {
        this.cantidadClientes = cantidadClientes;
    }

    public double getPromedioEdad() {
        return promedioEdad;
    }

    public void setPromedioEdad(double promedioEdad) {
        this.promedioEdad = promedioEdad;
    }
}
