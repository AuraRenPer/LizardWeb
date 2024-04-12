package mx.edu.uteq.HolaMundo.repository;

import java.util.List;
import mx.edu.uteq.HolaMundo.entity.OfertaEducativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OfertaEducativaRepo extends JpaRepository<OfertaEducativa, Long> {

    // Puedes agregar métodos personalizados si es necesario
    @Query("SELECT o FROM OfertaEducativa o JOIN FETCH o.ocupaciones")
    List<OfertaEducativa> findAllFetchOcupaciones();
    
    //public List<OfertaEducativa> findAllByActivo(boolean activo);
}
