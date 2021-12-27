package edu.egg.AgendaJJ.validations;

import edu.egg.AgendaJJ.entity.JudicialDivision;
import edu.egg.AgendaJJ.exception.TrialsException;
import edu.egg.AgendaJJ.repository.JudicialDivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class CourtServiceValidation {
    @Autowired
    private JudicialDivisionRepository judicialDivisionRepository;

    public static void validateCourtName(String name) throws TrialsException {
        if(name.trim().length()==0){
            throw new TrialsException("El nombre del tribunal no puede estar vacío.");
        }
    }

    public static void validateCourtPresident(String courtPresident) throws TrialsException{
        if(courtPresident.trim().length()==0){
            throw new TrialsException("El dato no puede estar vacío.");
        }
        if(!courtPresident.matches(("\\D*"))){
            throw new TrialsException("El dato no puede contener números.");
        }
    }

    public static void validateAddress(String address) throws TrialsException{
        if(address.trim().length()==0){
            throw new TrialsException("El dato no puede estar vacío.");
        }
    }

    public static void validatePhoneNumber(Long phoneNumber) throws TrialsException{
        if(phoneNumber.toString().trim().length()==0){
            throw new TrialsException("El dato no puede estar vacío.");
        }
        if(!phoneNumber.toString().matches(("\\d*"))){
            throw new TrialsException("El dato solo puede contener números.");
        }
    }

    public void validateJudicialDivision(JudicialDivision judicialDivision) throws TrialsException{
        if(judicialDivision == null){
            throw new TrialsException("El dato no puede estar vacío.");
        }
        if(Objects.isNull(judicialDivisionRepository.findByName(judicialDivision))){
            throw new TrialsException("El departamento judicial NO existe.");
        }
    }

}
