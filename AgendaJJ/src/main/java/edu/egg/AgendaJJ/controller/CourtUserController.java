package edu.egg.AgendaJJ.controller;

import edu.egg.AgendaJJ.entity.CourtUser;
import edu.egg.AgendaJJ.enums.UserRole;
import edu.egg.AgendaJJ.exception.TrialsException;
import edu.egg.AgendaJJ.service.CourtUserService;
import edu.egg.AgendaJJ.service.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

@Controller
public class CourtUserController {
      
    private PhotoService photoService;
    private CourtUserService courtUserService;
    private CourtUser courtUser;
    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    //login recibe tres posibles situaciones: cuando se genera un error, cuando se desloguea y recibir el principal para saber si está logueado el usuario
    //login viene de Security, del loginpage, es la ruta, no un html, lo que mapeamos.
    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Principal principal) {

        ModelAndView mav = new ModelAndView("login");
        if (error != null) {
            mav.addObject("error", "Correo o contraseña incorrecta");
        }
        if (logout != null) {
            mav.addObject("logout", "Sesión finalizada");
        }
        if (principal != null) {
            //imprimimos el correo
            LOGGER.info("Principal -> {}", principal.getName());
            mav.setViewName("redirect:/");
        }
        return mav;
    }

    @GetMapping("/signup")
    public ModelAndView signup(HttpServletRequest request, Principal principal) {

        ModelAndView mav = new ModelAndView("signup");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("success", flashMap.get("success"));
            mav.addObject("error", flashMap.get("error"));
            mav.addObject("name", flashMap.get("nombre"));
            mav.addObject("dni", flashMap.get("dni"));
            mav.addObject("mail", flashMap.get("mail"));
            mav.addObject("password", flashMap.get("password"));
            mav.addObject("jobTitle", flashMap.get("jobTitle"));
            mav.addObject("image", flashMap.get("image"));
        }

        if (principal != null) {
            LOGGER.info("Principal -> {}", principal.getName());
            mav.setViewName("redirect:/");
        }
        return mav;
    }

    @PostMapping("/registration")
    public RedirectView signup(@RequestParam String name, @RequestParam Long dni, @RequestParam String mail, @RequestParam String password, @RequestParam String jobTitle, @RequestParam UserRole userRole, @RequestParam Boolean status, String image, MultipartFile photo, RedirectAttributes attributes, HttpServletRequest request) throws TrialsException, ServletException {

        RedirectView redirectView = new RedirectView("/login");

        courtUserService.createUser(name, dni, mail, password, jobTitle, userRole, image, status, photo);
        attributes.addFlashAttribute("success", "Se ha registrado con éxito.");
        request.login(mail, password);
        redirectView.setUrl("/index");
        return redirectView;
    }

    @GetMapping("/courtUsers")
    public ModelAndView showCourtUsers(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("courtUsers");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("success", flashMap.get("success"));
            mav.addObject("error", flashMap.get("error"));
        }

        mav.addObject("courtUsers", courtUserService.readCourtUser());
        return mav;
    }

    @GetMapping("/create")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView createCourtUser(HttpServletRequest request, MultipartFile photo) throws TrialsException {

        ModelAndView mav = new ModelAndView("courtUser-form");

      if (!photo.isEmpty()) {
          courtUser.setImage(photoService.copyPhoto(photo));
          }

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("error", flashMap.get("error"));
            mav.addObject("usuario", flashMap.get("courtUser"));
        } else {
            mav.addObject("usuario", new CourtUser());
        }

        mav.addObject("title", "Crear Usuario");
        mav.addObject("action", "save");
        return mav;
    }

    @GetMapping("/update/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView updateCourtUser(@PathVariable Integer id, HttpSession session) {
        //Si el id coincide, vuelve a index logueado
        if (!session.getAttribute("id").equals(id)) {
            return new ModelAndView(new RedirectView("/index"));
        }
        ModelAndView mav = new ModelAndView("courtUser-form");
        mav.addObject("courtUser", courtUserService.findById(id));
        mav.addObject("title", "Editar Usuario");
        mav.addObject("action", "modificar");
        return mav;
    }

    @PostMapping("/save")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView saveCourtUser(@RequestParam String name, @RequestParam Long dni, @RequestParam String mail, @RequestParam String password, @RequestParam String jobTitle, @RequestParam UserRole userRole, @RequestParam Boolean status, String image, MultipartFile photo, RedirectAttributes attributes, HttpServletRequest request) throws TrialsException {
        try {
            courtUserService.createUser(name, dni, mail, password, jobTitle, userRole, image, status, photo);
            attributes.addFlashAttribute("success", "Usuario creado exitosamente");
        } catch (TrialsException e) {
            attributes.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/courtUsers");
    }

    @PostMapping("/update")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView update(@RequestParam String name, @RequestParam Long dni, @RequestParam String mail, @RequestParam String password, @RequestParam String jobTitle, @RequestParam UserRole userRole, @RequestParam Boolean status, @RequestParam String image, MultipartFile photo, RedirectAttributes attributes, HttpServletRequest request) throws TrialsException{
        if (!photo.isEmpty()) {
            courtUser.setImage(photoService.copyPhoto(photo));
        }
        courtUserService.createUser(name, dni, mail, password, jobTitle, userRole, image, status, photo);
        return new RedirectView("/courtUsers");
    }

    @PostMapping("/enable/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView enable(@PathVariable Integer id) {
        courtUserService.enable(id);
        return new RedirectView("/courtUsers");
    }

    @PostMapping("/delete/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView delete(@PathVariable Integer id) {
        courtUserService.deleteCourtUser(id);
        return new RedirectView("/usuarios");
    }
}