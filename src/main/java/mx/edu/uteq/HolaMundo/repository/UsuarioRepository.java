/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.edu.uteq.HolaMundo.repository;

import java.util.List;
import mx.edu.uteq.HolaMundo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    public Usuario findByUsername(String username);
    void deleteByUsername(String username);
    public Usuario findByCorreo(String correo);
    //public List<Usuario> findByEnabled(boolean enabled);
}
