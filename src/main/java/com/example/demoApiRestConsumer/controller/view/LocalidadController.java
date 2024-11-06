package com.example.demoApiRestConsumer.controller.view;

import com.example.demoApiRestConsumer.business.domain.dto.LocalidadDTO;
import com.example.demoApiRestConsumer.business.logic.error.ErrorServiceException;
import com.example.demoApiRestConsumer.business.logic.service.LocalidadService;
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
@RequestMapping("/localidad")
public class LocalidadController {
    @Autowired
    private LocalidadService localidadService;

    private String viewList = "view/localidad/listarLocalidad.html";
    private String redirectList = "redirect:/localidad/listarLocalidad";
    private String viewEdit = "view/localidad/editarLocalidad.html";

    /////////////////////////////////////////////
    /////////////////////////////////////////////
    ////////// VIEW: listarLocalidad ////////////
    /////////////////////////////////////////////
    /////////////////////////////////////////////

    @GetMapping("/listarLocalidad")
    public String listarLocalidad(Model model) {
        try {
            List<LocalidadDTO> listaLocalidad = localidadService.listar();
            model.addAttribute("listaLocalidad", listaLocalidad);
        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("msgError", "Error de Sistema");
        }
        return viewList;
    }

    @GetMapping("/altaLocalidad")
    public String alta(LocalidadDTO localidad, Model model) {

        localidad = new LocalidadDTO();
        localidad.setDenominacion("");

        model.addAttribute("localidad", localidad);
        model.addAttribute("isDisabled", false);

        return viewEdit;
    }

    @GetMapping("/consultar")
    public String consultar(@RequestParam long id, Model model) {

        try {

            LocalidadDTO localidad = localidadService.buscar(id);
            model.addAttribute("localidad", localidad);
            model.addAttribute("isDisabled", true);

            return viewEdit;

        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
            return viewList;
        }
    }

    @GetMapping("/modificar")
    public String modificar(@RequestParam Long id, Model model) {

        try {

            LocalidadDTO localidad = localidadService.buscar(id);
            model.addAttribute("localidad", localidad);
            model.addAttribute("isDisabled", false);

            return viewEdit;

        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
            return viewList;
        }
    }

    @GetMapping("/baja")
    public String baja(@RequestParam Long id, RedirectAttributes attributes, Model model) {

        try {

            localidadService.eliminar(id);
            attributes.addFlashAttribute("msgExito", "La acción fue realizada correctamente.");
            return redirectList;

        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
            return redirectList;
        }
    }

    /////////////////////////////////////////////
    /////////////////////////////////////////////
    //////////// VIEW: editLocalidad ////////////
    /////////////////////////////////////////////
    /////////////////////////////////////////////

    @PostMapping("/aceptarEditLocalidad")
    public String aceptarEdit(@RequestParam(required = false, defaultValue = "0") Long id,
                              @RequestParam String denominacion,
                              RedirectAttributes attributes, Model model) {

        try {

            if (id == 0)
                localidadService.crear(denominacion);
            else
                localidadService.modificar(id, denominacion);

            attributes.addFlashAttribute("msgExito", "La acción fue realizada correctamente.");
            return redirectList;

        } catch (ErrorServiceException e) {
            return error(e.getMessage(), model, id, denominacion);
        } catch (Exception e) {
            return error("Error de Sistema", model, id, denominacion);
        }

    }

    private String error(String mensaje, Model model, Long id, String denominacion) {
        try {

            model.addAttribute("msgError", mensaje);
            if (id == 0) {
                model.addAttribute("localidad", localidadService.buscar(id));
            } else {
                LocalidadDTO localidad = new LocalidadDTO();
                localidad.setDenominacion(denominacion);;
                model.addAttribute("localidad", localidad);
            }

        } catch (Exception e) {
        }
        return viewEdit;
    }

    @GetMapping("/cancelarEditLocalidad")
    public String cancelarEdit() {
        return redirectList;
    }

}