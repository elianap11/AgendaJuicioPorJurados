package edu.egg.AgendaJJ.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ErrorsController implements ErrorController {

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView errors(HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("error");
        String message = "";
        int code = response.getStatus();

        switch (code) {
            case 403:
                message = "No tienen permisos suficientes para acceder al recurso solicitado";
                break;
            case 404:
                message = "El recurso solicitado no fue encontrado";
                break;
            case 500:
                message = "Error interno en el servidor";
                break;
            default:
                message = "Error inesperado";
        }

        mav.addObject("codigo", code);
        mav.addObject("mensaje", message);
        return mav;
    }
}