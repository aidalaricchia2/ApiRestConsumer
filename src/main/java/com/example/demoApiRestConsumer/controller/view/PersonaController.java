package com.example.demoApiRestConsumer.controller.view;

import com.example.demoApiRestConsumer.business.domain.dto.PersonaDTO;
import com.example.demoApiRestConsumer.business.logic.error.ErrorServiceException;
import com.example.demoApiRestConsumer.business.logic.service.PersonaService;
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
@RequestMapping("/persona")
public class PersonaController {
    @Autowired
    private PersonaService personaService;

    private String viewList = "view/persona/listarPersona.html";
    private String redirectList = "redirect:/persona/listarPersona";
    private String viewEdit = "view/persona/editarPersona.html";

    /////////////////////////////////////////////
    /////////////////////////////////////////////
    /////////// VIEW: listarPersona /////////////
    /////////////////////////////////////////////
    /////////////////////////////////////////////

    @GetMapping("/listarPersona")
    public String listarPersona(Model model) {
        try {
            List<PersonaDTO> listaPersona = personaService.listar();
            model.addAttribute("listaPersona", listaPersona);
        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("msgError", "Error de Sistema");
        }
        return viewList;
    }

    @GetMapping("/altaPersona")
    public String alta(PersonaDTO persona, Model model) {

        persona = new PersonaDTO();
        persona.setNombre("");
        persona.setApellido("");
        persona.setDni(0);

        model.addAttribute("persona", persona);
        model.addAttribute("isDisabled", false);

        return viewEdit;
    }

    @GetMapping("/consultar")
    public String consultar(@RequestParam(value = "id") long idPersona, Model model) {

        try {

            PersonaDTO persona = personaService.buscar(idPersona);
            model.addAttribute("persona", persona);
            model.addAttribute("isDisabled", true);

            return viewEdit;

        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
            return viewList;
        }
    }

    @GetMapping("/modificar")
    public String modificar(@RequestParam(value = "id") Long idPersona, Model model) {

        try {

            PersonaDTO persona = personaService.buscar(idPersona);
            model.addAttribute("persona", persona);
            model.addAttribute("isDisabled", false);

            return viewEdit;

        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
            return viewList;
        }
    }

    @GetMapping("/baja")
    public String baja(@RequestParam(value = "id") Long idPersona, RedirectAttributes attributes, Model model) {

        try {

            personaService.eliminar(idPersona);
            attributes.addFlashAttribute("msgExito", "La acción fue realizada correctamente.");
            return redirectList;

        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
            return redirectList;
        }
    }

    /////////////////////////////////////////////
    /////////////////////////////////////////////
    ///////////// VIEW: eCategoria //////////////
    /////////////////////////////////////////////
    /////////////////////////////////////////////

    @PostMapping("/aceptarEditPersona")
    public String aceptarEdit(@RequestParam(required = false, defaultValue = "0") Long id, @RequestParam String nombre,
                              @RequestParam String apellido, @RequestParam int dni, RedirectAttributes attributes,
                              Model model) {

        try {

            if (dni == 0) {
                throw new ErrorServiceException("El DNI no puede ser 0 o negativo.");
            }

            if (id == 0)
                personaService.crear(nombre, apellido, dni);
            else
                personaService.modificar(id, nombre, apellido, dni);

            attributes.addFlashAttribute("msgExito", "La acción fue realizada correctamente.");
            return redirectList;

        } catch (ErrorServiceException e) {
            return error(e.getMessage(), model, id, nombre, apellido, dni);
        } catch (Exception e) {
            return error("Error de Sistema", model, id, nombre, apellido, dni);
        }

    }

    private String error(String mensaje, Model model, Long id, String nombre, String apellido, int dni) {
        try {

            model.addAttribute("msgError", mensaje);
            if (id == 0) {
                model.addAttribute("persona", personaService.buscar(id));
            } else {
                PersonaDTO persona = new PersonaDTO();
                persona.setNombre(nombre);
                persona.setApellido(apellido);
                persona.setDni(dni);
                model.addAttribute("persona", persona);
            }

        } catch (Exception e) {
        }
        return viewEdit;
    }

    @GetMapping("/cancelarEditPersona")
    public String cancelarEdit() {
        return redirectList;
    }

}
