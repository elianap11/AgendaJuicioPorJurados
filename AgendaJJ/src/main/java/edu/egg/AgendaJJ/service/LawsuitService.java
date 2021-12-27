package edu.egg.AgendaJJ.service;

import edu.egg.AgendaJJ.entity.*;
import edu.egg.AgendaJJ.exception.TrialsException;
import edu.egg.AgendaJJ.repository.LawsuitRepository;
import edu.egg.AgendaJJ.validations.LawsuitServiceValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import java.util.Optional;

@Service
public class LawsuitService {

    @Autowired
    private LawsuitRepository lawsuitRepository;

    @Transactional
    public void createLawsuit(String number, String caseTitle, Court court) throws TrialsException {
        Lawsuit lawsuit = new Lawsuit();

        LawsuitServiceValidation validation = new LawsuitServiceValidation();

        validation.validateNumber(number);
        lawsuit.setNumber(number);

        validation.validateCaseTitle(caseTitle);
        lawsuit.setCaseTitle(caseTitle);

        validation.validateCourt(court);
        lawsuit.setCourt(court);

        lawsuitRepository.save(lawsuit);
    }

    @Transactional(readOnly = true)
    public Lawsuit readLawsuit(Integer id) {
        Optional<Lawsuit> lawsuitOptional = lawsuitRepository.findById(id);
        return lawsuitOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Lawsuit> findLawsuit() {
        return lawsuitRepository.findAll();
    }

    @Transactional
    public void modifyLawsuit(Integer id, String number, String caseTitle, Court court) throws TrialsException {
        LawsuitServiceValidation validation = new LawsuitServiceValidation();

        validation.validateNumber(number);

        validation.validateCaseTitle(caseTitle);

        validation.validateCourt(court);

        lawsuitRepository.modifyLawsuit(id, number, caseTitle, court);
    }

    @Transactional(readOnly = true)
    public List<Lawsuit> findByCaseTitle(String caseTitle) {
        return lawsuitRepository.findByCaseTitle(caseTitle);
    }

    @Transactional(readOnly = true)
    public List<Lawsuit> findByDateTrial(Integer month) {
        return lawsuitRepository.findByDateTrial(month);
    }
}

