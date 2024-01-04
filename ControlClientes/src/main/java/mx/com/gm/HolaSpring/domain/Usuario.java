package mx.com.gm.HolaSpring.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@NoArgsConstructor
@Table(name="usuario")
public class Usuario implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    @NotEmpty
    private String username;
    
    @NotEmpty
    private String password;
    
    //Esto le dirá a Hibernate que aplique todas las operaciones de persistencia 
    //(como guardar, actualizar, eliminar, etc.) al rol cuando se apliquen al usuario.
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id_usuario")
    private List<Rol> roles;
    
    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
