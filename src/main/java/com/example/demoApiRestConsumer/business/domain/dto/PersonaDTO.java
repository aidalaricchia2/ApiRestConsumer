package com.example.demoApiRestConsumer.business.domain.dto;

import lombok.Data;

@Data
public class PersonaDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private int dni;
}
