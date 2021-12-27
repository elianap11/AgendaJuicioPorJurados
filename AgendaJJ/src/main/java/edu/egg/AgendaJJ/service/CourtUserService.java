package edu.egg.AgendaJJ.service;

import edu.egg.AgendaJJ.entity.CourtUser;
import edu.egg.AgendaJJ.enums.UserRole;
import edu.egg.AgendaJJ.exception.TrialsException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;

import edu.egg.AgendaJJ.repository.CourtUserRepository;
import edu.egg.AgendaJJ.validations.CourtUserServiceValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class CourtUserService implements UserDetailsService {

    @Autowired
    private CourtUserRepository courtUserRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    
    @Autowired
    private PhotoService photoService;

    @Autowired
    private EmailService emailService;

    private final String message = "No existe un usuario registrado con el correo %s";

    @Transactional
    public void createUser(String name, Long dni, String mail, String password, String jobTitle, UserRole userRole, String image, Boolean status, MultipartFile photo) throws TrialsException {

        if (courtUserRepository.existsByMail(mail)) {
            throw new TrialsException("Ya existe un usuario asociado con el correo ingresado");
        }

        CourtUser courtUser = new CourtUser();

        CourtUserServiceValidation.validateName(name);
        courtUser.setName(name);

        CourtUserServiceValidation.validateDni(dni);
        courtUser.setDni(dni);

        CourtUserServiceValidation.validateEmail(mail);
        courtUser.setMail(mail);

        CourtUserServiceValidation.validatePassword(password);
        courtUser.setPassword(encoder.encode(password));

        CourtUserServiceValidation.validateJobTitle(name);
        courtUser.setJobTitle(jobTitle);

        CourtUserServiceValidation.validateRol(userRole);
        courtUser.setUserRole(UserRole.USER);
       
        //Esto es: el primero es admin, los restantes user
        if (courtUserRepository.findAll().isEmpty()) {
            courtUser.setUserRole(UserRole.ADMIN);
        } else {
           courtUser.setUserRole(UserRole.USER);
        }
      
        if (!photo.isEmpty()) {
            courtUser.setImage(photoService.copyPhoto(photo));
        }

        courtUser.setStatus(true);

        courtUserRepository.save(courtUser);
        emailService.sendThread(mail);
    }

    @Transactional
    public void update(Integer id, String name, Long dni, String mail, String password, String jobTitle, UserRole userRole, String image, Boolean status, MultipartFile photo) throws TrialsException {
        Optional<CourtUser> courtUserOptional = courtUserRepository.findById(id);
        if (courtUserOptional.isPresent()) {
            CourtUser courtUser = courtUserOptional.get();

            CourtUserServiceValidation.validateName(name);
            courtUser.setName(name);

            CourtUserServiceValidation.validateDni(dni);
            courtUser.setDni(dni);

            CourtUserServiceValidation.validateEmail(mail);
            courtUser.setMail(mail);

            CourtUserServiceValidation.validatePassword(password);
            courtUser.setPassword(encoder.encode(password));

            CourtUserServiceValidation.validateJobTitle(name);
            courtUser.setJobTitle(jobTitle);

            CourtUserServiceValidation.validateRol(userRole);
            courtUser.setUserRole(userRole);

            if (!photo.isEmpty()) {
                courtUser.setImage(photoService.copyPhoto(photo));
                //photoService.deletePhoto(courtUser.getImage());
            }

            courtUserRepository.save(courtUser);
        } else {
            throw new TrialsException("No se encontró al usuario.");
        }
    }

    @Transactional(readOnly = true)
    public List<CourtUser> readCourtUser() {
        return courtUserRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CourtUser findById(Integer id) {
        return courtUserRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public CourtUser findByMail(String mail) {
        return courtUserRepository.findByMail(mail).orElse(null);
    }

    @Transactional
    public void enable(Integer id) {
        courtUserRepository.enable(id);
    }

    @Transactional
    public void deleteCourtUser(Integer id) {
        courtUserRepository.deleteById(id);
    }

    @Override
    //Este método entra en juego cuando el usuario se loguea
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        //chequea que el correo exista: permite el acceso o lanza una excepción
        CourtUser courtUser = courtUserRepository.findByMail(mail)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(message, mail)));
        //La palabra ROLE_ (es la forma que reconoce los roles Spring) concatenada con el rol y el nombre de ese rol
        //Acá genera los permisos y se los pasa al User
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + courtUser.getUserRole().name());

        //El Servlet se castea
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        //Si la sesión no está creada, con true la va a crear
        HttpSession session = attributes.getRequest().getSession(true);

        session.setAttribute("id", courtUser.getId());
        session.setAttribute("name", courtUser.getName());
        session.setAttribute("dni", courtUser.getDni());
        session.setAttribute("jobTitle", courtUser.getJobTitle());
        session.setAttribute("userRol", courtUser.getUserRole().name());
        session.setAttribute("image", courtUser.getImage());

        //le paso las autorizaciones en el collections
        return new User(courtUser.getMail(), courtUser.getPassword(), Collections.singletonList(authority));
    }
}