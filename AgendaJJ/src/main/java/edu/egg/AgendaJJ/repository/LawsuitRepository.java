package edu.egg.AgendaJJ.repository;

import edu.egg.AgendaJJ.entity.Court;
import edu.egg.AgendaJJ.entity.Lawsuit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LawsuitRepository extends JpaRepository<Lawsuit, Integer> {

    @Modifying
    @Query("UPDATE Lawsuit l SET l.number = :number, l.caseTitle = :caseTitle, l.court = :court WHERE l.id = :id")
    void modifyLawsuit(@Param("id") Integer id, @Param("number") String number, @Param("caseTitle") String caseTitle, @Param("court") Court court);

    @Query(value = "SELECT * FROM lawsuit WHERE caseTitle LIKE '%?1%'", nativeQuery = true)
    List<Lawsuit> findByCaseTitle(String caseTitle);

    @Query(value = "SELECT * FROM lawsuit WHERE id = (SELECT lawsuit_id FROM trial WHERE MONTH(date_Trial) = ?1) ", nativeQuery = true)
    List<Lawsuit> findByDateTrial(Integer month);

    public Lawsuit findByNumber(Lawsuit lawsuit);
}