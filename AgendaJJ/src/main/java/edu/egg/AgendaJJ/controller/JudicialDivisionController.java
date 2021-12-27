package edu.egg.AgendaJJ.controller;

import edu.egg.AgendaJJ.entity.JudicialDivision;
import edu.egg.AgendaJJ.entity.Trial;
import edu.egg.AgendaJJ.exception.TrialsException;
import edu.egg.AgendaJJ.service.JudicialDivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/judicialDivision")
public class JudicialDivisionController {

    @Autowired
    private JudicialDivisionService judicialDivisionService;

    private JudicialDivision judicialDivision;

    @GetMapping
    public ModelAndView showAll(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("judicialDivisions");

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("success", flashMap.get("success"));
            mav.addObject("error", flashMap.get("error"));
        }

        mav.addObject("judicialDivisions", judicialDivisionService.findJudicialDivisions());
        return mav;
    }


    @GetMapping("/create")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView createJudicialDivision(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("judicialDivision-form");
        mav.addObject("judicialDivision", new JudicialDivision());
        mav.addObject("judicialDivisions", judicialDivisionService.findJudicialDivisions());
        mav.addObject("title", "Crear Departamento Judicial");
        mav.addObject("action", "save");
        return mav;
    }

    @PostMapping("/save")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView saveJudicialDivision(@RequestParam String name, RedirectAttributes redirectAttributes) throws TrialsException {
        try {
            judicialDivisionService.createJudicialDivision(name);
            redirectAttributes.addFlashAttribute("success", "El Departamento Judicial se guardó exitosamente.");
        } catch (TrialsException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return new RedirectView("/judicialDivision/create");
        }
        return new RedirectView("/judicialDivision");
    }
}