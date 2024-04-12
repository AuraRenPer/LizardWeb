/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.edu.uteq.HolaMundo.repository;

import mx.edu.uteq.HolaMundo.entity.Horario;
import org.springframework.data.repository.CrudRepository;


public interface HorarioRepo extends CrudRepository<Horario, Long> {
    
}
