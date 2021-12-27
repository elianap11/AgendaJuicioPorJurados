package edu.egg.AgendaJJ.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class PrincipalController {

    @GetMapping("/index")
    public ModelAndView index(HttpSession session) {
        return new ModelAndView("index");
    }
}