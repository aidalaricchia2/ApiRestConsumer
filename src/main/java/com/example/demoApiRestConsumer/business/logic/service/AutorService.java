package com.example.demoApiRestConsumer.business.logic.service;

import com.example.demoApiRestConsumer.business.domain.dto.AutorDTO;
import com.example.demoApiRestConsumer.business.logic.error.ErrorServiceException;
import com.example.demoApiRestConsumer.business.persistence.rest.AutorDAORest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    @Autowired
    private AutorDAORest dao;

    public void validar(String nombre, String apellido, String biografia) throws ErrorServiceException {

        try {

            if (nombre == null || nombre.isEmpty()) {
                throw new ErrorServiceException("Debe indicar el nombre");
            }

            if (apellido == null || apellido.isEmpty()) {
                throw new ErrorServiceException("Debe indicar el apellido");
            }

            if (biografia == null || biografia.isEmpty()) {
                throw new ErrorServiceException("Debe indicar la biografía");
            }

        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public void crear(String nombre, String apellido, String biografia) throws ErrorServiceException {

        try {

            validar(nombre, apellido, biografia);

            AutorDTO autor = new AutorDTO();
            autor.setNombre(nombre);
            autor.setApellido(apellido);
            autor.setBiografia(biografia);

            dao.crear(autor);

        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public void modificar(Long id, String nombre, String apellido, String biografia) throws ErrorServiceException {

        try {

            validar(nombre, apellido, biografia);

            AutorDTO autor = new AutorDTO();
            autor.setId(id);
            autor.setNombre(nombre);
            autor.setApellido(apellido);
            autor.setBiografia(biografia);

            dao.actualizar(autor);

        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public AutorDTO buscar (Long id) throws ErrorServiceException {

        try {

            if (id == 0) {
                throw new ErrorServiceException("Debe indicar el autor");
            }

            AutorDTO autor = dao.buscar(id);

            return autor;

        } catch (ErrorServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de sistema");
        }
    }

    public void eliminar(Long id) throws ErrorServiceException {

        try {

            if (id == 0) {
                throw new ErrorServiceException("Debe indicar el categoria");
            }

            dao.eliminar(id);

        } catch (ErrorServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de sistema");
        }

    }

    public List<AutorDTO> listar() throws ErrorServiceException {
        try {
            return dao.listar();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de sistema");
        }
    }

}