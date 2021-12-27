package edu.egg.AgendaJJ.service;

import edu.egg.AgendaJJ.entity.Court;
import edu.egg.AgendaJJ.entity.JudicialDivision;
import edu.egg.AgendaJJ.exception.TrialsException;
import edu.egg.AgendaJJ.repository.CourtRepository;
import edu.egg.AgendaJJ.validations.CourtServiceValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourtService {

    @Autowired
    private CourtRepository courtRepository;

    @Transactional
    public void createCourt(String name, String courtPresident, String address, Long phoneNumber, JudicialDivision judicialDivision) throws TrialsException {
        CourtServiceValidation validation = new CourtServiceValidation();

        Court court = new Court();

        validation.validateCourtName(name);
        court.setName(name);

        validation.validateCourtPresident(courtPresident);
        court.setCourtPresident(courtPresident);

        validation.validateAddress(address);
        court.setAddress(address);

        validation.validatePhoneNumber(phoneNumber);
        court.setPhoneNumber(phoneNumber);

        validation.validateJudicialDivision(judicialDivision);
        court.setJudicialDivision(judicialDivision);

        courtRepository.save(court);
    }

    @Transactional(readOnly = true)
    public Court readCourt(Integer id) {
        Optional<Court> courtOptional = courtRepository.findById(id);
        return courtOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Court> findCourts() {
        return courtRepository.findAll();
    }

    @Transactional
    public void updateCourt(Integer id, String name, String courtPresident, String address, Long phoneNumber, JudicialDivision judicialDivision) throws TrialsException {

        Court court = courtRepository.findById(id).orElse(null);
        if (court == null) {
            throw new TrialsException("No existe el Tribunal");
        }

        CourtServiceValidation validation = new CourtServiceValidation();

        validation.validateCourtName(name);

        validation.validateCourtPresident(courtPresident);

        validation.validateAddress(address);

        validation.validatePhoneNumber(phoneNumber);

        validation.validateJudicialDivision(judicialDivision);

        courtRepository.updateCourt(id, name, courtPresident, address, phoneNumber, judicialDivision);
    }

}
