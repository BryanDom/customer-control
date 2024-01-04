package mx.com.gm.HolaSpring.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

//nos agregar get y set, el constructor, HasCode, toString.
//dejamos muy limpio el codigo con data, javabin.
@Data
//nuestra clase en entidad
@Entity
//pueden existir error al mapeo de la tabla
@Table(name = "persona")
public class Persona implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersona;
    
    //valida cadena vacia y el not null solo que no sea nulo, asi que no sirve
    @NotEmpty
    private String nombre;
    
    @NotEmpty
    private String apellido;
    
    @NotEmpty
    @Email
    private String email;
    
    
    private String telefono;
    
    @NotNull
    private Double saldo;
}
