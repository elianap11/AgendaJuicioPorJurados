package edu.egg.AgendaJJ.repository;

import edu.egg.AgendaJJ.entity.Court;
import edu.egg.AgendaJJ.entity.JudicialDivision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtRepository extends JpaRepository <Court, Integer> {

    @Modifying
    @Query(value = "UPDATE Court SET name = name, courtPresident = courtPresident, address = address, phoneNumber = phoneNumber, JudicialDivision = judicialDivision WHERE id = '?1'", nativeQuery = true)
    void updateCourt(@Param("id") Integer id, @Param("name") String name, @Param("courtPresident") String courtPresident, @Param("address") String address, @Param("phoneNumber") Long phoneNumber, @Param("JudicialDivision") JudicialDivision judicialDivision);

    public Court findByName(Court court);
}
