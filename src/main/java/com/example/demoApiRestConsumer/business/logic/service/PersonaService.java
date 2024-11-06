package com.example.demoApiRestConsumer.business.logic.service;

import com.example.demoApiRestConsumer.business.domain.dto.PersonaDTO;
import com.example.demoApiRestConsumer.business.logic.error.ErrorServiceException;
import com.example.demoApiRestConsumer.business.persistence.rest.PersonaDAORest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaService {

    @Autowired
    private PersonaDAORest dao;

    public void validar(String nombre, String apellido, int dni) throws ErrorServiceException {

        try {

            if (nombre == null || nombre.isEmpty()) {
                throw new ErrorServiceException("Debe indicar el nombre");
            }

            if (nombre == null || nombre.isEmpty()) {
                throw new ErrorServiceException("Debe indicar el nombre");
            }

            if (dni == 0) {
                throw new ErrorServiceException("Debe indicar el DNI");
            }

        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public void crear(String nombre, String apellido, int dni) throws ErrorServiceException {

        try {

            validar(nombre, apellido, dni);

            PersonaDTO persona = new PersonaDTO();
            persona.setNombre(nombre);
            persona.setApellido(apellido);
            persona.setDni(dni);

            dao.crear(persona);

        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public void modificar(Long idPersona, String nombre, String apellido, int dni) throws ErrorServiceException {

        try {

            validar(nombre, apellido, dni);

            PersonaDTO persona = new PersonaDTO();
            persona.setId(idPersona);
            persona.setNombre(nombre);
            persona.setApellido(apellido);
            persona.setDni(dni);

            dao.actualizar(persona);

        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public PersonaDTO buscar (Long id) throws ErrorServiceException {

        try {

            if (id == 0) {
                throw new ErrorServiceException("Debe indicar la persona");
            }

            PersonaDTO persona = dao.buscar(id);

            return persona;

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

    public List<PersonaDTO> listar() throws ErrorServiceException {
        try {
            return dao.listar();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de sistema");
        }
    }

}
