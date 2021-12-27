package edu.egg.AgendaJJ.validations;

import edu.egg.AgendaJJ.enums.UserRole;
import edu.egg.AgendaJJ.exception.TrialsException;

public class CourtUserServiceValidation {
    public static void validateName(String name) throws TrialsException {
        if(name.trim().length()==0){
            throw new TrialsException("El dato no puede estar vacío.");
        }
        if(!name.matches(("^[a-zA-ZÀ-ÿ\\\\u00f1\\\\u00d1]+(\\\\s*[a-zA-ZÀ-ÿ\\\\u00f1\\\\u00d1]*)*[a-zA-ZÀ-ÿ\\\\u00f1\\\\u00d1]+$"))){
            throw new TrialsException("El dato no puede contener números ni carácteres especiales.");
        }
    }

    public static void validateDni(Long DNI) throws TrialsException {
        if(DNI.toString().trim().length()==0){
            throw new TrialsException("El dato no puede estar vacío.");
        }
        if(!DNI.toString().matches(("\\d*"))){
            throw new TrialsException("El dato solo puede contener números.");
        }
        if(DNI.toString().trim().length()!=8){
            throw new TrialsException("El DNI debe poseer 8 dígitos.");
        }
    }

    public static void validateEmail(String email) throws TrialsException {
        if(email.trim().length()==0){
            throw new TrialsException("El dato no puede estar vacío.");
        }
        if(!email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$") ){
            throw new TrialsException("La email ingresado es inválido.");
        }
    }

    public static void validatePassword(String pass) throws TrialsException{
        if(pass.trim().length()==0){
            throw new TrialsException("El dato no puede estar vacío.");
        }
    }

    public static void validateJobTitle(String jobTitle) throws TrialsException{
        if(jobTitle.trim().length()==0){
            throw new TrialsException("El dato no puede estar vacío.");
        }
    }

    public static void validateRol(UserRole userRole) throws TrialsException{
        if (userRole != UserRole.USER || userRole != UserRole.ADMIN){
            throw new TrialsException("Se debe indicar un rol válido.");
        }
    }

}
