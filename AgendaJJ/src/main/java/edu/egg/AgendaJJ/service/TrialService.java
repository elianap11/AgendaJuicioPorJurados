package edu.egg.AgendaJJ.service;

import edu.egg.AgendaJJ.entity.*;
import edu.egg.AgendaJJ.exception.TrialsException;
import edu.egg.AgendaJJ.repository.TrialRepository;
import edu.egg.AgendaJJ.validations.TrialServiceValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Service
public class TrialService {

    @Autowired
    private TrialRepository trialRepository;

    @Transactional
    public void createTrial(LocalDate dateTrial, Court court, Lawsuit lawsuit, JudicialDivision judicialDivision) throws TrialsException {
        Trial trial = new Trial();

        TrialServiceValidation validation = new TrialServiceValidation();

        trial.setDateTrial(dateTrial);

        validation.validateCourt(court);
        trial.setCourt(court);

        validation.validateLawsuit(lawsuit);
        trial.setLawsuit(lawsuit);

        validation.validateJudicialDivision(judicialDivision);
        trial.setJudicialDivision(judicialDivision);
        trial.setStatus(true);

        trialRepository.save(trial);
    }

    @Transactional(readOnly = true)
    public Trial readTrial(Integer id) {
        Optional<Trial> trialOptional = trialRepository.findById(id);
        return trialOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Trial> findTrials() {
        return trialRepository.findAll();
    }

    @Transactional
    public void modifyTrial(Integer id, LocalDate dateTrial, Court court, Lawsuit lawsuit, JudicialDivision judicialDivision) throws TrialsException {
        Trial trial = trialRepository.findById(id).orElse(null);
        if (trial == null) {
            throw new TrialsException("No existe el juicio.");
        }

        TrialServiceValidation validation = new TrialServiceValidation();

        validation.validateCourt(court);

        validation.validateLawsuit(lawsuit);

        validation.validateJudicialDivision(judicialDivision);

        trialRepository.modifyTrial(id, dateTrial, court, lawsuit, judicialDivision);
    }

    @Transactional
    public void deleteTrial(Integer id) {
        trialRepository.deleteById(id);
    }

    @Transactional
    public void enableTrial(Integer id) {
        trialRepository.enableTrial(id);
    }

    @Transactional(readOnly = true)
    public List<Trial> findByJudicialDivision(JudicialDivision judicialDivision) {
        return trialRepository.findByJudicialDivision(judicialDivision);
    }
}