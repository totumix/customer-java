package com.customer.customer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    private String nombreCompleto;
    private String documentoIdentidad;
    private String correoElectronico;
    @JsonFormat(pattern = "yyyy-MM-dd") // Define el formato de fecha que deseas
    private LocalDate fechaNacimiento;
}
