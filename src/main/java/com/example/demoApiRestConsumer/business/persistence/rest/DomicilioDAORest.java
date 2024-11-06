package com.example.demoApiRestConsumer.business.persistence.rest;

import com.example.demoApiRestConsumer.business.domain.dto.DomicilioDTO;

import com.example.demoApiRestConsumer.business.logic.error.ErrorServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@Service
public class DomicilioDAORest {

    @Autowired
    private RestTemplate restTemplate;

    public void crear(DomicilioDTO domicilio) throws ErrorServiceException {
        try {
            String uri = "http://localhost:9000/api/v1/domicilios";
            restTemplate.postForEntity(uri, domicilio, DomicilioDTO.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public void actualizar(DomicilioDTO domicilio) throws ErrorServiceException {
        try {
            String uri = "http://localhost:9000/api/v1/domicilios/" + domicilio.getId(); // Asegúrate de tener un método getId en DomicilioDTO
            restTemplate.put(uri, domicilio);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public void eliminar(Long id) throws ErrorServiceException {
        try {
            String uri = "http://localhost:9000/api/v1/domicilios/" + id;
            restTemplate.delete(uri);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public DomicilioDTO buscar(Long id) throws ErrorServiceException {
        try {
            String uri = "http://localhost:9000/api/v1/domicilios/" + id;
            ResponseEntity<DomicilioDTO> response = restTemplate.getForEntity(uri, DomicilioDTO.class);
            return response.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public List<DomicilioDTO> listar() throws ErrorServiceException {
        try {
            String uri = "http://localhost:9000/api/v1/domicilios";
            ResponseEntity<DomicilioDTO[]> response = restTemplate.getForEntity(uri, DomicilioDTO[].class);
            DomicilioDTO[] domicilios = response.getBody();
            return Arrays.asList(domicilios);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }
}
