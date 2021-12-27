package edu.egg.AgendaJJ.validations;

import edu.egg.AgendaJJ.exception.TrialsException;

public class JudicialDivisionServiceValidation {
    public static void validateName(String name) throws TrialsException {
        if(name.trim().length()==0){
            throw new TrialsException("El dato no puede estar vacío.");
        }
        if(name.matches("^[a-zA-ZÀ-ÿ\\\\u00f1\\\\u00d1]+(\\\\s*[a-zA-ZÀ-ÿ\\\\u00f1\\\\u00d1]*)*[a-zA-ZÀ-ÿ\\\\u00f1\\\\u00d1]+$")){
            throw new TrialsException("El departamento judicial no puede contener números ni carácteres especiales.");
        }
    }
}
