package edu.egg.AgendaJJ.validations;

import edu.egg.AgendaJJ.entity.Court;
import edu.egg.AgendaJJ.exception.TrialsException;
import edu.egg.AgendaJJ.repository.CourtRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class LawsuitServiceValidation {
    @Autowired
    private CourtRepository courtRepository;

    public void validateNumber(String number) throws TrialsException {
        if(number.trim().length()==0){
            throw new TrialsException("El nombre del tribunal no puede estar vacío.");
        }
    }

    public void validateCaseTitle(String caseTitle) throws TrialsException {
        if(caseTitle.trim().length()==0){
            throw new TrialsException("El nombre del tribunal no puede estar vacío.");
        }
    }

    public void validateCourt(Court court) throws TrialsException {
        if(court == null){
            throw new TrialsException("El dato no puede estar vacío.");
        }
        if(Objects.isNull(courtRepository.findByName(court))){
            throw new TrialsException("El tribunal NO existe.");
        }
    }
}

