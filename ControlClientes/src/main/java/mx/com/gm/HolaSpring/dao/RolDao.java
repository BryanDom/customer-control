package mx.com.gm.HolaSpring.dao;

import mx.com.gm.HolaSpring.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolDao extends JpaRepository<Rol, Long>{
    
}
