package com.example.demoApiRestConsumer.controller.view;

import com.example.demoApiRestConsumer.business.domain.dto.AutorDTO;
import com.example.demoApiRestConsumer.business.logic.error.ErrorServiceException;
import com.example.demoApiRestConsumer.business.logic.service.AutorService;
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
@RequestMapping("/autor")
public class AutorController {
    @Autowired
    private AutorService autorService;

    private String viewList = "view/autor/listarAutor.html";
    private String redirectList = "redirect:/autor/listarAutor";
    private String viewEdit = "view/autor/editarAutor.html";

    /////////////////////////////////////////////
    /////////////////////////////////////////////
    //////////// VIEW: listarAutor //////////////
    /////////////////////////////////////////////
    /////////////////////////////////////////////

    @GetMapping("/listarAutor")
    public String listarAutor(Model model) {
        try {
            List<AutorDTO> listaAutor = autorService.listar();
            model.addAttribute("listaAutor", listaAutor);
        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("msgError", "Error de Sistema");
        }
        return viewList;
    }

    @GetMapping("/altaAutor")
    public String alta(AutorDTO autor, Model model) {

        autor = new AutorDTO();
        autor.setNombre("");
        autor.setApellido("");
        autor.setBiografia("");

        model.addAttribute("autor", autor);
        model.addAttribute("isDisabled", false);

        return viewEdit;
    }

    @GetMapping("/consultar")
    public String consultar(@RequestParam long id, Model model) {

        try {

            AutorDTO autor = autorService.buscar(id);
            model.addAttribute("autor", autor);
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

            AutorDTO autor = autorService.buscar(id);
            model.addAttribute("autor", autor);
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

            autorService.eliminar(id);
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

    @PostMapping("/aceptarEditAutor")
    public String aceptarEdit(@RequestParam(required = false, defaultValue = "0") Long id,
                              @RequestParam String nombre,
                              @RequestParam String apellido,
                              @RequestParam String biografia,
                              RedirectAttributes attributes, Model model) {

        try {

            if (id == 0)
                autorService.crear(nombre, apellido, biografia);
            else
                autorService.modificar(id, nombre, apellido, biografia);

            attributes.addFlashAttribute("msgExito", "La acción fue realizada correctamente.");
            return redirectList;

        } catch (ErrorServiceException e) {
            return error(e.getMessage(), model, id, nombre, apellido, biografia);
        } catch (Exception e) {
            return error("Error de Sistema", model, id, nombre, apellido, biografia);
        }

    }

    private String error(String mensaje, Model model, Long id, String nombre, String apellido, String biografia) {
        try {

            model.addAttribute("msgError", mensaje);
            if (id == 0) {
                model.addAttribute("autor", autorService.buscar(id));
            } else {
                AutorDTO autor = new AutorDTO();
                autor.setNombre(nombre);
                autor.setApellido(apellido);
                autor.setBiografia(biografia);
                model.addAttribute("autor", autor);
            }

        } catch (Exception e) {
        }
        return viewEdit;
    }

    @GetMapping("/cancelarEditAutor")
    public String cancelarEdit() {
        return redirectList;
    }

}
