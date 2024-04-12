/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package mx.edu.uteq.HolaMundo.controller;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import mx.edu.uteq.HolaMundo.entity.Admision;
import mx.edu.uteq.HolaMundo.entity.OfertaEducativa;
import mx.edu.uteq.HolaMundo.repository.AdmisionRepo;
import mx.edu.uteq.HolaMundo.repository.OfertaEducativaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Slf4j
public class ControladorCliente {

    @Autowired
    private AdmisionRepo AdminRepo;
    @Autowired
    private OfertaEducativaRepo OfertaRepo;
    String menuInicio = "";
    String menuOferta = "";
    String menuAdmisiones = "";

    @GetMapping("/cliente")
    public String clienteInicio(Model model) {
        menuInicio = "nav-link active fw-bold";
        menuOferta = "nav-link";
        menuAdmisiones = "nav-link";
        model.addAttribute("styleInicio", menuInicio);
        model.addAttribute("styleOferta", menuOferta);
        model.addAttribute("styleAdminiones", menuAdmisiones);
        return "cliente";
    }

    //Visualizar admisiones
    @GetMapping("/admisiones-cliente")
    public String admisionesCliente(Model model) {
        menuInicio = "nav-link";
        menuOferta = "nav-link";
        menuAdmisiones = "nav-link active fw-bold";
        model.addAttribute("styleInicio", menuInicio);
        model.addAttribute("styleOferta", menuOferta);
        model.addAttribute("styleAdminiones", menuAdmisiones);
        return "admisiones";
    }

    @RequestMapping(value = "/api/admisiones-cliente", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List obtenerAdmisionesCliente() {
        List<Admision> lista = (List<Admision>) AdminRepo.findAll();
        return lista;
    }

    //VISUALIZAR OFERTA EDUCATIVA
    @GetMapping("/ofertaeducativa-cliente")
    public String paginaOfertaEducativa(Model model) {
        menuInicio = "nav-link";
        menuOferta = "nav-link active fw-bold";
        menuAdmisiones = "nav-link";
        List<OfertaEducativa> datos = (List<OfertaEducativa>) OfertaRepo.findAll();

        model.addAttribute("styleInicio", menuInicio);
        model.addAttribute("styleOferta", menuOferta);
        model.addAttribute("styleAdminiones", menuAdmisiones);
        model.addAttribute("datos", datos);

        return "ofertaeducativa";
    }

    @RequestMapping(value = "/api/ofertaeducativa-cliente", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List obtenerOfertas() {
        List<OfertaEducativa> lista = (List<OfertaEducativa>) OfertaRepo.findAll();
        return lista;
    }
}
