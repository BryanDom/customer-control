package mx.com.gm.HolaSpring.web;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mx.com.gm.HolaSpring.dao.RolDao;
import mx.com.gm.HolaSpring.dao.UsuarioDao;
import mx.com.gm.HolaSpring.domain.Persona;
import mx.com.gm.HolaSpring.domain.Rol;
import mx.com.gm.HolaSpring.domain.Usuario;
import mx.com.gm.HolaSpring.servicio.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class ControladorInicio {

    //para inyectar cualquier dependendica es solo hacer esto
    @Autowired
    private PersonaService personaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioDao userRepository;
    
    @Autowired
    private RolDao rolRepository;

    @GetMapping("/")
    public String inicio(Model model, @AuthenticationPrincipal User user) {
        var personas = personaService.listarPersonas();

        log.info("ejecutando el controlador Spring MVC");
        log.info("usuario que hizo login:" + user);
        model.addAttribute("personas", personas);
        var saldoTotal = 0D;
        for (var p : personas) {
            saldoTotal += p.getSaldo();
        }
        model.addAttribute("saldoTotal", saldoTotal);
        model.addAttribute("totalClientes", personas.size());
        return "index";
    }

    @GetMapping("/agregar")
    public String agregar(Persona persona) {
        return "modificar";
    }

    //los atributos deben de estar siempre juntos
    @PostMapping("/guardar")
    public String guardar(@Valid Persona persona, Errors errores) {
        if (errores.hasErrors()) {
            return "modificar";
        }

        personaService.guardar(persona);
        return "redirect:/";
    }

    @GetMapping("/editar/{idPersona}")
    public String editar(Persona persona, Model model) {
        persona = personaService.encontrarPersona(persona);
        model.addAttribute("persona", persona);
        return "modificar";
    }

    @GetMapping("/eliminar")
    public String eliminar(Persona persona) {
        personaService.eliminar(persona);
        return "redirect:/";
    }

    @GetMapping("/registrar")
    public String register(Usuario usuario) {
        return "agregarUsuario";
    }

    //los atributos deben de estar siempre juntos
    @PostMapping("/registrar")
    public String guardar(@ModelAttribute Usuario usuario) {
        Usuario newUser = new Usuario(usuario.getUsername(), passwordEncoder.encode(usuario.getPassword()));

        // Crear un nuevo rol con el nombre por defecto
        Rol defaultRole = new Rol();
        defaultRole.setNombre("ROLE_USER");

        // Guardar el rol en la base de datos
        rolRepository.save(defaultRole);

        // Agregar el rol por defecto a la lista de roles del usuario
        newUser.setRoles(Arrays.asList(defaultRole));

        userRepository.save(newUser);
        return "redirect:/login";
    }

}
