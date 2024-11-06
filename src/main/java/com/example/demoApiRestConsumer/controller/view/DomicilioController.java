package com.example.demoApiRestConsumer.controller.view;

import com.example.demoApiRestConsumer.business.domain.dto.DomicilioDTO;
import com.example.demoApiRestConsumer.business.logic.error.ErrorServiceException;
import com.example.demoApiRestConsumer.business.logic.service.DomicilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/domicilio")
public class DomicilioController {

    @Autowired
    private DomicilioService domicilioService;

    private String viewList = "view/domicilio/listarDomicilio.html";
    private String redirectList = "redirect:/domicilio/listarDomicilio";
    private String viewEdit = "view/domicilio/editarDomicilio.html";

    /////////////////////////////////////////////
    /////////////////////////////////////////////
    /////////// VIEW: listarDomicilio /////////////
    /////////////////////////////////////////////
    /////////////////////////////////////////////

    @GetMapping("/listarDomicilio")
    public String listarDomicilio(Model model) {
        try {
            List<DomicilioDTO> listaDomicilio = domicilioService.listar();
            model.addAttribute("listaDomicilio", listaDomicilio);
        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("msgError", "Error de Sistema");
        }
        return viewList;
    }

    @GetMapping("/altaDomicilio")
    public String alta(DomicilioDTO domicilio, Model model) {
        try {
            domicilio = new DomicilioDTO();
            domicilio.setCalle("");
            domicilio.setNumero(0);
            domicilio.setLocalidad(null); // Inicializa según tu lógica

            model.addAttribute("domicilio", domicilio);
            model.addAttribute("isDisabled", false);

            return viewEdit;
        } catch (Exception e) {
            // Manejo de error: registrar y mostrar un mensaje apropiado
            model.addAttribute("msgError", "Ocurrió un error al cargar el formulario.");
            return "error"; // Nombre de la vista de error
        }
    }


    @GetMapping("/consultar")
    public String consultar(@RequestParam(value = "id") long idDomicilio, Model model) {
        try {
            DomicilioDTO domicilio = domicilioService.buscar(idDomicilio);
            model.addAttribute("domicilio", domicilio);
            model.addAttribute("isDisabled", true);
            return viewEdit;
        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
            return viewList;
        }
    }

    @GetMapping("/modificar")
    public String modificar(@RequestParam(value = "id") Long idDomicilio, Model model) {
        try {
            DomicilioDTO domicilio = domicilioService.buscar(idDomicilio);
            model.addAttribute("domicilio", domicilio);
            model.addAttribute("isDisabled", false);
            return viewEdit;
        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
            return viewList;
        }
    }

    @GetMapping("/baja")
    public String baja(@RequestParam(value = "id") Long idDomicilio, RedirectAttributes attributes, Model model) {
        try {
            domicilioService.eliminar(idDomicilio);
            attributes.addFlashAttribute("msgExito", "La acción fue realizada correctamente.");
            return redirectList;
        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
            return redirectList;
        }
    }

    /////////////////////////////////////////////
    /////////////////////////////////////////////
    ///////////// VIEW: eDomicilio //////////////
    /////////////////////////////////////////////
    /////////////////////////////////////////////

    @PostMapping("/aceptarEditDomicilio")
    public String aceptarEdit(@RequestParam(required = false, defaultValue = "0") Long id,
                              @RequestParam String calle, @RequestParam int numero,
                              @RequestParam Long idLocalidad, RedirectAttributes attributes,
                              Model model) {
        try {
            if (numero <= 0) {
                throw new ErrorServiceException("El número debe ser positivo.");
            }

            if (id == 0) {
                domicilioService.crear(calle, numero, idLocalidad);
            } else {
                domicilioService.modificar(id, calle, numero, idLocalidad);
            }

            attributes.addFlashAttribute("msgExito", "La acción fue realizada correctamente.");
            return redirectList;

        } catch (ErrorServiceException e) {
            return error(e.getMessage(), model, id, calle, numero, idLocalidad);
        } catch (Exception e) {
            return error("Error de Sistema", model, id, calle, numero, idLocalidad);
        }
    }

    private String error(String mensaje, Model model, Long id, String calle, int numero, Long idLocalidad) {
        try {
            model.addAttribute("msgError", mensaje);
            if (id == 0) {
                model.addAttribute("domicilio", new DomicilioDTO());
            } else {
                DomicilioDTO domicilio = domicilioService.buscar(id);
                model.addAttribute("domicilio", domicilio);
            }
        } catch (Exception e) {
            // Manejo de excepciones
        }
        return viewEdit;
    }

    @GetMapping("/cancelarEditDomicilio")
    public String cancelarEdit() {
        return redirectList;
    }
}