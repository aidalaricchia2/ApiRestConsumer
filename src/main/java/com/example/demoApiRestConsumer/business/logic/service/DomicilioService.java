package com.example.demoApiRestConsumer.business.logic.service;

import com.example.demoApiRestConsumer.business.domain.dto.DomicilioDTO;
import com.example.demoApiRestConsumer.business.domain.dto.LocalidadDTO;
import com.example.demoApiRestConsumer.business.logic.error.ErrorServiceException;
import com.example.demoApiRestConsumer.business.persistence.rest.DomicilioDAORest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomicilioService {

    @Autowired
    private DomicilioDAORest dao;

    public void validar(String calle, int numero, Long idLocalidad) throws ErrorServiceException {
        try {
            if (calle == null || calle.isEmpty()) {
                throw new ErrorServiceException("Debe indicar la calle");
            }
            if (numero <= 0) {
                throw new ErrorServiceException("El número debe ser positivo");
            }
            if (idLocalidad == null || idLocalidad <= 0) {
                throw new ErrorServiceException("Debe indicar una localidad válida");
            }
        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public void crear(String calle, int numero, Long idLocalidad) throws ErrorServiceException {
        try {
            validar(calle, numero, idLocalidad);

            DomicilioDTO domicilio = new DomicilioDTO();
            domicilio.setCalle(calle);
            domicilio.setNumero(numero);

            // Supongo que necesitarás setear el LocalidadDTO aquí
            LocalidadDTO localidad = new LocalidadDTO(); // Asegúrate de tener el constructor adecuado en LocalidadDTO
            localidad.setId(idLocalidad); // Debes tener un método setId en LocalidadDTO
            domicilio.setLocalidad(localidad);

            dao.crear(domicilio);
        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public void modificar(Long idDomicilio, String calle, int numero, Long idLocalidad) throws ErrorServiceException {
        try {
            validar(calle, numero, idLocalidad);

            DomicilioDTO domicilio = new DomicilioDTO();
            domicilio.setId(idDomicilio); // Asegúrate de tener un método setId en DomicilioDTO
            domicilio.setCalle(calle);
            domicilio.setNumero(numero);

            LocalidadDTO localidad = new LocalidadDTO();
            localidad.setId(idLocalidad);
            domicilio.setLocalidad(localidad);

            dao.actualizar(domicilio);
        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public DomicilioDTO buscar(Long id) throws ErrorServiceException {
        try {
            if (id == null || id <= 0) {
                throw new ErrorServiceException("Debe indicar un domicilio válido");
            }
            return dao.buscar(id);
        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de sistema");
        }
    }

    public void eliminar(Long id) throws ErrorServiceException {
        try {
            if (id == null || id <= 0) {
                throw new ErrorServiceException("Debe indicar un domicilio válido");
            }
            dao.eliminar(id);
        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de sistema");
        }
    }

    public List<DomicilioDTO> listar() throws ErrorServiceException {
        try {
            return dao.listar();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de sistema");
        }
    }
}
