/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package mx.edu.uteq.HolaMundo.controller;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mx.edu.uteq.HolaMundo.entity.OcupacionProfesional;
import mx.edu.uteq.HolaMundo.entity.OfertaEducativa;
import mx.edu.uteq.HolaMundo.repository.OfertaEducativaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControladorOfertaEducativa {

    @Autowired
    private OfertaEducativaRepo repo;
    String menuInicio = "";
    String menuOferta = "";
    String menuAdmisiones = "";
    String menuUsuarios = "";

    @GetMapping("/ofertaeducativa")
    public String paginaOfertaEducativa(Model model) {
        menuInicio = "nav-link";
        menuOferta = "nav-link active fw-bold";
        menuAdmisiones = "nav-link";
        menuUsuarios = "nav-link";
        //List<OfertaEducativa> oferta = repo.findAll();
        model.addAttribute("styleInicio", menuInicio);
        model.addAttribute("styleOferta", menuOferta);
        model.addAttribute("styleAdminiones", menuAdmisiones);
        model.addAttribute("styleUsuarios", menuUsuarios);
        //model.addAttribute("datos", oferta);
        return "ofertaeducativa";
    }

    @GetMapping("/agregarOferta")
    public String mostrarPaginaAgregarOferta(Model model) {
        model.addAttribute("oferta", new OfertaEducativa());
        model.addAttribute("styleInicio", menuInicio);
        model.addAttribute("styleOferta", menuOferta);
        model.addAttribute("styleAdminiones", menuAdmisiones);
        model.addAttribute("styleUsuarios", menuUsuarios);
        return "agregarOferta";
    }

    @PostMapping("/guardar-oferta")
    public String guardarOferta(@Valid @ModelAttribute("oferta") OfertaEducativa oferta, Errors errores) {
        if (errores.hasErrors()) {
            return "agregarOferta";
        }
        repo.save(oferta);
        return "redirect:/ofertaeducativa";
    }

    @GetMapping("/modificarOferta/{id}")
    public String mostrarPaginaModificarOferta(@PathVariable Long id, Model model) {
        OfertaEducativa oferta = repo.findById(id).orElse(null);

        if (oferta != null) {

            List<OcupacionProfesional> ocupaciones = oferta.getOcupaciones();

            model.addAttribute("oferta", oferta);
            model.addAttribute("ocupaciones", ocupaciones);
            model.addAttribute("styleInicio", menuInicio);
            model.addAttribute("styleOferta", menuOferta);
            model.addAttribute("styleAdminiones", menuAdmisiones);
            model.addAttribute("styleUsuarios", menuUsuarios);

            return "modificarOferta";
        } else {
            return "redirect:/ofertaeducativa";
        }
    }

    @PostMapping("/guardar-modificacion-oferta")
    public String guardarModificacionOferta(@Valid @ModelAttribute("oferta") OfertaEducativa oferta, Errors errores) {
        if (errores.hasErrors()) {
            return "redirect:/modificarOferta";
        }
        OfertaEducativa ofertaExistente = repo.findById(oferta.getId()).orElse(null);

        if (ofertaExistente != null) {
            oferta.setOcupaciones(ofertaExistente.getOcupaciones());
            repo.save(oferta);

            return "redirect:/ofertaeducativa";
        } else {
            return "redirect:/ofertaeducativa";
        }
    }

    @GetMapping("/eliminarOferta/{id}")
    public String eliminarOferta(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/ofertaeducativa";
    }

    @GetMapping(value = "/api/oferta-educativa")
    public String obtenerOfertas(Model model) {
        List<OfertaEducativa> lista = repo.findAll();
        model.addAttribute("datos", lista);
        return "ofertaeducativa::tbl-oferta";
    }

    @RequestMapping("/api/guardar-oferta")
    public String guardar(@Valid @ModelAttribute("oferta") OfertaEducativa oferta,
            @RequestParam(name = "ocupa", required = false) String[] ocupaciones,
            @RequestParam(name = "idOc", required = false) String[] idOc, Errors errores) {

        if (errores.hasErrors()) {
            return "modificarOferta";
        }

        // Verificar si se proporciona un ID válido
        if (oferta.getId() != null) {
            Optional<OfertaEducativa> ofertaExistenteOptional = repo.findById(oferta.getId());
            if (ofertaExistenteOptional.isPresent()) {
                // Si la oferta existe, actualiza sus datos
                OfertaEducativa ofertaExistente = ofertaExistenteOptional.get();
                ofertaExistente.setOfertaEducativa(oferta.getOfertaEducativa());
                ofertaExistente.setActivo(oferta.isActivo()); // Actualiza el campo activo

                // Actualiza las ocupaciones si se proporcionan
                if (ocupaciones != null) {
                    List<OcupacionProfesional> listaO = new ArrayList<>();
                    for (int i = 0; i < ocupaciones.length; i++) {
                        OcupacionProfesional o = new OcupacionProfesional();
                        o.setId(Integer.parseInt(idOc[i]));
                        o.setOcupacion(ocupaciones[i]);
                        listaO.add(o);
                    }
                    ofertaExistente.setOcupaciones(listaO);
                } else {
                    //Elimina si ocupaciones es null o vacio
                    ofertaExistente.setOcupaciones(null);
                }
                // Guarda la oferta actualizada
                repo.save(ofertaExistente);
            }
        } else {
            // Si no se proporciona un ID, guarda una nueva oferta
            if (ocupaciones != null) {
                List<OcupacionProfesional> listaO = new ArrayList<>();
                for (int i = 0; i < ocupaciones.length; i++) {
                    OcupacionProfesional o = new OcupacionProfesional();
                    o.setId(Integer.parseInt(idOc[i]));
                    o.setOcupacion(ocupaciones[i]);
                    listaO.add(o);
                }
                oferta.setOcupaciones(listaO);
            }
            repo.save(oferta);
        }

        return "redirect:/ofertaeducativa";
    }

}