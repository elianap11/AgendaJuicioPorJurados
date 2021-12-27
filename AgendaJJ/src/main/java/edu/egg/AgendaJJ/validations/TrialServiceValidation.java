package edu.egg.AgendaJJ.validations;

import edu.egg.AgendaJJ.entity.Court;
import edu.egg.AgendaJJ.entity.JudicialDivision;
import edu.egg.AgendaJJ.entity.Lawsuit;
import edu.egg.AgendaJJ.exception.TrialsException;
import edu.egg.AgendaJJ.repository.CourtRepository;
import edu.egg.AgendaJJ.repository.JudicialDivisionRepository;
import edu.egg.AgendaJJ.repository.LawsuitRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Objects;

public class TrialServiceValidation {
    @Autowired
    private JudicialDivisionRepository judicialDivisionRepository;

    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private LawsuitRepository lawsuitRepository;

    public void validateDate(LocalDate dateTrial) throws TrialsException{
        if(dateTrial.getYear() != 2022){
            throw new TrialsException("El año debe ser 2022.");
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

    public void validateLawsuit(Lawsuit lawsuit) throws TrialsException {
        if(lawsuit == null){
            throw new TrialsException("El dato no puede estar vacío.");
        }
        if(Objects.isNull(lawsuitRepository.findByNumber(lawsuit))){
            throw new TrialsException("La carátula NO existe.");
        }
    }


    public void validateJudicialDivision(JudicialDivision judicialDivision) throws TrialsException {
        if(judicialDivision == null){
            throw new TrialsException("El dato no puede estar vacío.");
        }
        if(Objects.isNull(judicialDivisionRepository.findByName(judicialDivision))){
            throw new TrialsException("La división Judicial NO existe.");
        }
    }

}