package com.example.demoApiRestConsumer.business.persistence.rest;

import com.example.demoApiRestConsumer.business.domain.dto.PersonaDTO;
import com.example.demoApiRestConsumer.business.logic.error.ErrorServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PersonaDAORest {

    @Autowired
    private RestTemplate restTemplate;

    public void crear(PersonaDTO persona) throws ErrorServiceException {
        try {
            String uri = "http://localhost:9000/api/v1/personas";
            restTemplate.postForEntity(uri, persona, PersonaDTO.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public void actualizar(PersonaDTO persona) throws ErrorServiceException {

        try {

            String uri = "http://localhost:9000/api/v1/personas/"+ persona.getId();
            restTemplate.put(uri, persona);

        } catch (Exception ex){
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public void eliminar(Long id)  throws ErrorServiceException {

        try {

            String uri = "http://localhost:9000/api/v1/personas/" + id;
            restTemplate.delete(uri);

        } catch (Exception ex){
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public PersonaDTO buscar(Long id)  throws ErrorServiceException {

        try {

            String uri = "http://localhost:9000/api/v1/personas/" + id;

            ResponseEntity<PersonaDTO> response = restTemplate.getForEntity(uri,PersonaDTO.class);
            PersonaDTO persona = response.getBody();

            return  persona;

        } catch (Exception ex){
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public List<PersonaDTO> listar() throws ErrorServiceException {
        try {
            String uri = "http://localhost:9000/api/v1/personas";

            ResponseEntity<PersonaDTO[]> response = restTemplate.getForEntity(uri, PersonaDTO[].class);
            PersonaDTO[] personas = response.getBody();
            List<PersonaDTO> listaPersonas = Arrays.asList(personas);
            return listaPersonas;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }
}
