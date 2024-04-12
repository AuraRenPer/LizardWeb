/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.uteq.HolaMundo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class WebSecuriyConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> {
                    authz.requestMatchers("/").permitAll()
                            .requestMatchers("/usuarios/**").hasRole("ADMIN")
                            .requestMatchers("/carousel/**").permitAll()
                            .requestMatchers("/recuperaContra").permitAll()
                            .requestMatchers("/enviarCorreo").permitAll()
                            .requestMatchers("/paginaToken").permitAll()
                            .requestMatchers("/carrusel2").permitAll()
                            .requestMatchers("/pantallaconfirmacion").permitAll()
                            .requestMatchers("/restablecerContra").permitAll()
                            .requestMatchers("/guardarContra").permitAll()
                            .requestMatchers("/confirmacioncontra").permitAll()
                            .requestMatchers("/tokennotfound").permitAll()
                            .requestMatchers("/cliente").permitAll()
                            //.requestMatchers("/ofertaeducativa/**").hasRole("ADMIN")
                            .requestMatchers("/guardar-admision/**").hasRole("ADMIN")
                            .requestMatchers("/api/pastel**").hasRole("ADMIN")
                            .requestMatchers("/agregarOferta/**")
                            .hasAnyRole("ADMIN", "COORDINADOR")
                            .requestMatchers("/alumnos_eliminar/**").hasRole("ADMIN")
                            .requestMatchers("/api/admisiones-cliente").permitAll() // Permitir acceso a la API de admisiones
                            .anyRequest().authenticated();
                }
                )
                .formLogin((form) -> form
                .loginPage("/login")
                .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

}
