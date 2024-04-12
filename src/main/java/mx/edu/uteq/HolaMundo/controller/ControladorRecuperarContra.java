/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.uteq.HolaMundo.controller;

import java.time.LocalDateTime;
import java.util.List;
import mx.edu.uteq.HolaMundo.entity.Admision;
import mx.edu.uteq.HolaMundo.entity.OfertaEducativa;
import mx.edu.uteq.HolaMundo.entity.Usuario;
import mx.edu.uteq.HolaMundo.repository.UsuarioRepository;
import mx.edu.uteq.HolaMundo.utility.EncriptarPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ControladorRecuperarContra {

    @Autowired
    private UsuarioRepository repo;
    String menuInicio = "";
    String menuOferta = "";
    String menuAdmisiones = "";
    String menuUsuarios = "";
    @Autowired
    private JavaMailSender emailS;

    /*
    @GetMapping("/recuperaContra")
    public String paginaRecuperaContra(Model model) {
        model.addAttribute("correoForm", new UsuarioCorreoForm());
        return "recuperacontra";
    }
     */
    @GetMapping("/recuperaContra")
    public String paginaRecuperaContra(Model model) {
        return "recuperacontra";
    }

    @PostMapping("/enviarCorreo")
    public String enviarCorreoFunc(@RequestParam("correo") String correo, Model model) {
        System.out.println("Correo proporcionado: " + correo);

        // Buscar usuario por correo
        Usuario usuario = repo.findByCorreo(correo);

        if (usuario != null) {
            String token = usuario.getPassword();
            String tokengenerado = EncriptarPassword.encriptarPassword(token);
            usuario.setToken(tokengenerado);
            
            usuario.setFechaCreacionToken(LocalDateTime.now());//Fecha y hora en que se creó el token

            String correoP = correo;
            String asunto = "Recuperación de contraseña";
            String mensaje = "Ingresa al siguiente link para recuperar tu contraseña: http://localhost:8080/restablecerContra este es tu token de confirmación: "+tokengenerado;
            

            SimpleMailMessage mensajeCorreo = new SimpleMailMessage();
            mensajeCorreo.setTo(correoP);
            mensajeCorreo.setSubject(asunto);
            mensajeCorreo.setText(mensaje);
            emailS.send(mensajeCorreo);
            
            repo.save(usuario);

            return "redirect:/pantallaconfirmacion";
        } else {
            // Manejar el caso cuando no se encuentra ningún usuario con ese correo
            return "redirect:/carusel3"; // O algún otro nombre de la página de error
        }
    }

    
    // Clase auxiliar para manejar el correo en el formulario
    public static class UsuarioCorreoForm {

        private String correo;

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }
    }

    @GetMapping("/restablecerContra")
    public String paginaRestablecerContra(Model model) {
        return "restablecercontra";
    }
    
    @PostMapping("/guardarContra")
    public String guardarContra(@RequestParam("username") String username, 
        @RequestParam("token") String token,
        @RequestParam("contra") String contra, 
        @RequestParam("contra2") String contra2, 
        Model model) {
        System.out.println("Usuario proporcionado: " + username);
        
        // Buscar usuario
        Usuario usuario = repo.findByUsername(username);
        
        // Verificar si el usuario es null
        if (usuario == null) {
            model.addAttribute("error", "Verifica que tu nombre de usuario sea correcto");
            return "error_modal"; // Debes tener una vista llamada error_modal para mostrar el mensaje de error.
        } 
        
        String tokenusuario = usuario.getToken();
        LocalDateTime fechaCreacionToken = usuario.getFechaCreacionToken();

        if (!tokenusuario.equals(token)) {
            model.addAttribute("error", "El token es incorrecto");
            return "error_modal"; // Debes tener una vista llamada error_modal para mostrar el mensaje de error.
        }

        if (fechaCreacionToken.plusMinutes(2).isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "El token ha vencido");
            return "error_modal"; // Debes tener una vista llamada error_modal para mostrar el mensaje de error.
        }
        
        String cambiarpassword = EncriptarPassword.encriptarPassword(contra2);
        usuario.setPassword(cambiarpassword);
        
        String cambiartoken = usuario.getPassword();
        String reemplazartoken = EncriptarPassword.encriptarPassword(cambiartoken);
        usuario.setToken(reemplazartoken);
       
        repo.save(usuario);
        
        return "redirect:/confirmacioncontra";
    }
    

    @GetMapping("/paginaToken")
    public String paginaGeneraToken(Model model) {
        return "tokenpantalla";
    }
    

    @GetMapping("/pantallaconfirmacion")
    public String paginaDeConfirmacion(Model model) {
        return "correoenviado";
    }
    
    
    @GetMapping("/confirmacioncontra")
    public String contraRestaurada(Model model) {
        return "confirmacioncontra";
    }
    
    
    @GetMapping("/tokennotfound")
    public String tokenNotFound(Model model) {
        return "tokennotfound";
    }
    
}
