package com.example.demoApiRestConsumer.business.persistence.rest;

import com.example.demoApiRestConsumer.business.domain.dto.AutorDTO;
import com.example.demoApiRestConsumer.business.logic.error.ErrorServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class AutorDAORest {

    @Autowired
    private RestTemplate restTemplate;

    public void crear(AutorDTO autor) throws ErrorServiceException {
        try {
            String uri = "http://localhost:9000/api/v1/autores";
            restTemplate.postForEntity(uri, autor, AutorDTO.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public void actualizar(AutorDTO autor) throws ErrorServiceException {

        try {

            String uri = "http://localhost:9000/api/v1/autores/"+ autor.getId();
            restTemplate.put(uri, autor);

        } catch (Exception ex){
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public void eliminar(Long id)  throws ErrorServiceException {

        try {

            String uri = "http://localhost:9000/api/v1/autores/" + id;
            restTemplate.delete(uri);

        } catch (Exception ex){
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public AutorDTO buscar(Long id)  throws ErrorServiceException {

        try {

            String uri = "http://localhost:9000/api/v1/autores/" + id;

            ResponseEntity<AutorDTO> response = restTemplate.getForEntity(uri,AutorDTO.class);
            AutorDTO autor  = response.getBody();

            return  autor;

        } catch (Exception ex){
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public List<AutorDTO> listar() throws ErrorServiceException {
        try {
            String uri = "http://localhost:9000/api/v1/autores";

            ResponseEntity<AutorDTO[]> response = restTemplate.getForEntity(uri, AutorDTO[].class);
            AutorDTO[] autores = response.getBody();
            List<AutorDTO> listaAutores = Arrays.asList(autores);
            return listaAutores;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }
}
