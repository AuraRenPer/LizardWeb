/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.uteq.HolaMundo.service;

import java.util.ArrayList;
import java.util.List;
import mx.edu.uteq.HolaMundo.entity.Rol;
import mx.edu.uteq.HolaMundo.entity.Usuario;
import mx.edu.uteq.HolaMundo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;



@Service("userDetailsService")
public class UsuarioDetailsService implements UserDetailsService{
    @Autowired
    private UsuarioRepository repo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = repo.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Rol rol:user.getAuthorities()){
            authorities.add(new SimpleGrantedAuthority(rol.getAuthority()));
        }
        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
    }
    
}
