package com.example.demoApiRestConsumer.business.domain.dto;
import lombok.Data;

@Data
public class DomicilioDTO {
    private Long id;
    private String calle;
    private int numero;
    private LocalidadDTO localidad;
}
