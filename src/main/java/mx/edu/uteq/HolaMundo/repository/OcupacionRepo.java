package mx.edu.uteq.HolaMundo.repository;

import mx.edu.uteq.HolaMundo.entity.OcupacionProfesional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OcupacionRepo extends JpaRepository<OcupacionProfesional, Long> {
    // Puedes agregar métodos personalizados si es necesario
}
