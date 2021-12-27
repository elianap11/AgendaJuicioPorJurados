package edu.egg.AgendaJJ.repository;

import edu.egg.AgendaJJ.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrialRepository extends JpaRepository<Trial, Integer> {

    @Modifying
    @Query("UPDATE Trial t SET t.dateTrial =: dateTrial, t.court =: court, t.lawsuit =: lawsuit, t.judicialDivision =: judicialDivision WHERE t.id =: id")
    void modifyTrial(@Param("id") Integer id, @Param("dateTrial") LocalDate dateTrial, @Param("Court") Court court, @Param("Lawsuit") Lawsuit lawsuit, @Param("JudicialDivision") JudicialDivision judicialDivision);

    @Modifying
    @Query("UPDATE Trial t SET t.status = true WHERE t.id = :id")
    void enableTrial(@Param("id") Integer id);

    //Por departamento judicial
    @Query(value = "SELECT * FROM trial WHERE court_id = (SELECT id FROM court WHERE judicial_division_id = " +
            "(SELECT id FROM judicial_division WHERE name = ?1))"
            , nativeQuery = true)
    List<Trial> findByJudicialDivision(JudicialDivision judicialDivision);
}
