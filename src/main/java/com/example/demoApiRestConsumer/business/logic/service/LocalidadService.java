package com.example.demoApiRestConsumer.business.logic.service;

import com.example.demoApiRestConsumer.business.domain.dto.LocalidadDTO;
import com.example.demoApiRestConsumer.business.logic.error.ErrorServiceException;
import com.example.demoApiRestConsumer.business.persistence.rest.LocalidadDAORest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalidadService {

    @Autowired
    private LocalidadDAORest dao;

    public void validar(String denominacion) throws ErrorServiceException {

        try {

            if (denominacion == null || denominacion.isEmpty()) {
                throw new ErrorServiceException("Debe indicar la denominacion");
            }

        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public void crear(String denominacion) throws ErrorServiceException {

        try {

            validar(denominacion);

            LocalidadDTO localidad = new LocalidadDTO();
            localidad.setDenominacion(denominacion);

            dao.crear(localidad);

        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public void modificar(Long id, String denominacion) throws ErrorServiceException {

        try {

            validar(denominacion);

            LocalidadDTO localidad = new LocalidadDTO();
            localidad.setId(id);
            localidad.setDenominacion(denominacion);


            dao.actualizar(localidad);

        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public LocalidadDTO buscar (Long id) throws ErrorServiceException {

        try {

            if (id == 0) {
                throw new ErrorServiceException("Debe indicar la localidad");
            }

            LocalidadDTO localidad = dao.buscar(id);

            return localidad;

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

    public List<LocalidadDTO> listar() throws ErrorServiceException {
        try {
            return dao.listar();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de sistema");
        }
    }

}
