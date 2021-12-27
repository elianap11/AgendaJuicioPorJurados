package edu.egg.AgendaJJ.controller;

import edu.egg.AgendaJJ.entity.Court;
import edu.egg.AgendaJJ.entity.JudicialDivision;
import edu.egg.AgendaJJ.exception.TrialsException;
import edu.egg.AgendaJJ.service.CourtService;
import edu.egg.AgendaJJ.service.JudicialDivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/court")
public class CourtController {

    @Autowired
    private CourtService courtService;

    @Autowired
    private JudicialDivisionService judicialDivisionService;

    @GetMapping
    public ModelAndView showCourts(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("courts");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("success", flashMap.get("success"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("courts", courtService.findCourts());
        return mav;
    }

    @GetMapping("/create")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView createCourt(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("court-form");
        mav.addObject("court", new Court());
        mav.addObject("judicialDivisions", judicialDivisionService.findJudicialDivisions());
        mav.addObject("title", "Crear Tribunal");
        mav.addObject("action", "save");
        return mav;
    }

    @GetMapping("/update/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editCourt(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("court-form");
        mav.addObject("court", courtService.readCourt(id));
        mav.addObject("judicialDivisions", judicialDivisionService.findJudicialDivisions());
        mav.addObject("title", "Editar Tribunal");
        mav.addObject("action", "update");
        return mav;
    }

    @PostMapping("/save")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView saveCourt(@RequestParam String name, @RequestParam String courtPresident, @RequestParam String address, @RequestParam Long phoneNumber, @RequestParam JudicialDivision judicialDivision, RedirectAttributes redirectAttributes) throws TrialsException {
        try {
            courtService.createCourt(name, courtPresident, address, phoneNumber, judicialDivision);
            redirectAttributes.addFlashAttribute("success", "El tirbunal se guardó exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return new RedirectView("/court/create");
        }
        return new RedirectView("/court");
    }

    @PostMapping("/update")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView updateCourt(@RequestParam Integer id, @RequestParam String name, @RequestParam String courtPresident, @RequestParam String address, @RequestParam Long phoneNumber, @RequestParam JudicialDivision judicialDivision, RedirectAttributes redirectAttributes) throws TrialsException {
        try {
            courtService.updateCourt(id, name, courtPresident, address, phoneNumber, judicialDivision);
            redirectAttributes.addFlashAttribute("success", "El tribunal fue modificado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return new RedirectView("/court");
    }
}