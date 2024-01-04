
package mx.com.gm.HolaSpring.dao;

import mx.com.gm.HolaSpring.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonaDao extends JpaRepository<Persona, Long> {
    
}
