package edu.egg.AgendaJJ.controller;

import edu.egg.AgendaJJ.entity.Court;
import edu.egg.AgendaJJ.entity.JudicialDivision;
import edu.egg.AgendaJJ.entity.Lawsuit;
import edu.egg.AgendaJJ.entity.Trial;
import edu.egg.AgendaJJ.exception.TrialsException;
import edu.egg.AgendaJJ.service.CourtService;
import edu.egg.AgendaJJ.service.JudicialDivisionService;
import edu.egg.AgendaJJ.service.LawsuitService;
import edu.egg.AgendaJJ.service.TrialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/trial")
public class TrialController {

    @Autowired
    private TrialService trialService;

    @Autowired
    private CourtService courtService;

    @Autowired
    private LawsuitService lawsuitService;

    @Autowired
    private JudicialDivisionService judicialDivisionService;

    @GetMapping
    public ModelAndView showTrials(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("trials");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("success", flashMap.get("success-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }
        mav.addObject("trials", trialService.findTrials());
        return mav;
    }

    @GetMapping("/create")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView createTrial() {
        ModelAndView mav = new ModelAndView("trial-form");
        mav.addObject("trial", new Trial());
        mav.addObject("courts", courtService.findCourts());
        mav.addObject("lawsuits", lawsuitService.findLawsuit());
        mav.addObject("judicialDivision", judicialDivisionService.findJudicialDivisions());
        mav.addObject("title", "Crear Juicio");
        mav.addObject("action", "save");
        return mav;
    }

    @GetMapping("/update/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editTrial(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("trial-form");
        mav.addObject("trial", trialService.readTrial(id));
        mav.addObject("courts", courtService.findCourts());
        mav.addObject("lawsuits", lawsuitService.findLawsuit());
        mav.addObject("judicialDivision", judicialDivisionService.findJudicialDivisions());
        mav.addObject("title", "Editar Juicio");
        mav.addObject("action", "update");
        return mav;
    }

    @PostMapping("/save")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView saveTrial(@RequestParam LocalDate dateTrial, @RequestParam Court court, @RequestParam Lawsuit lawsuit, @RequestParam JudicialDivision judicialDivision, RedirectAttributes redirectAttributes) throws TrialsException {
        try {
            trialService.createTrial(dateTrial, court, lawsuit, judicialDivision);
            redirectAttributes.addFlashAttribute("success-name", "El juicio se guardó exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error-name", e.getMessage());
            return new RedirectView("/trial/create");
        }
        return new RedirectView("/trials");
    }

    @PostMapping("/update")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView update(@RequestParam Integer id, @RequestParam LocalDate dateTrial, @RequestParam Court court, @RequestParam Lawsuit lawsuit, @RequestParam JudicialDivision judicialDivision, RedirectAttributes redirectAttributes) throws TrialsException {
        try {
            trialService.modifyTrial(id, dateTrial, court, lawsuit, judicialDivision);
            redirectAttributes.addFlashAttribute("success-name", "El juicio fue modificado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/trials");
    }

    @PostMapping("/delete/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView delete(@PathVariable Integer id) {
        trialService.deleteTrial(id);
        return new RedirectView("/trials");
    }

    @PostMapping("/enable/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView enable(@PathVariable Integer id) {
        trialService.enableTrial(id);
        return new RedirectView("/trials");
    }
}