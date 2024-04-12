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


@Controller
@Slf4j
public class ControladorInicio {
    
    @Autowired
    private OfertaEducativaRepo repo;
    @Autowired
    private AdmisionRepo repo2;
    
    String menuInicio = "";
    String menuOferta = "";
    String menuAdmisiones = "";
    String menuUsuarios = "";

    @GetMapping("/")
    public String page(Model model) {
        menuInicio = "nav-link active fw-bold";
        menuOferta = "nav-link";
        menuAdmisiones = "nav-link";
        menuUsuarios = "nav-link";
        model.addAttribute("styleInicio", menuInicio);
        model.addAttribute("styleOferta", menuOferta);
        model.addAttribute("styleAdminiones", menuAdmisiones);
        model.addAttribute("styleUsuarios", menuUsuarios);
        return "index";
    }
    
    @GetMapping("/login")
    public String mostrarPaginaLogin(Model model) {
        // Puedes personalizar esta parte según tus necesidades
        return "login"; // Esto asume que tienes una vista llamada "login"
    }
    
    
    @GetMapping("/carousel")
    public String paginaOfertaEducativa(Model model) {
        menuInicio = "nav-link";
        menuOferta = "nav-link active fw-bold";
        menuAdmisiones = "nav-link";
        
        List<OfertaEducativa> datos = (List<OfertaEducativa>) repo.findAll();
        List<Admision> datosadmision = (List<Admision>) repo2.findAll();

        model.addAttribute("styleInicio", menuInicio);
        model.addAttribute("styleOferta", menuOferta);
        model.addAttribute("styleAdminiones", menuAdmisiones);
        model.addAttribute("datos", datos);
        model.addAttribute("datosadmision", datosadmision);

        return "carousel";
    }

}
