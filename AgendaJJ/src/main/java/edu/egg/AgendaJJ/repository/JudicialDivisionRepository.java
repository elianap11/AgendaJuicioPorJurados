package edu.egg.AgendaJJ.repository;

import edu.egg.AgendaJJ.entity.JudicialDivision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JudicialDivisionRepository extends JpaRepository <JudicialDivision, Integer> {
    public JudicialDivision findByName(JudicialDivision judicialDivision);
}
