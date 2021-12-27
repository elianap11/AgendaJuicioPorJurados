package edu.egg.AgendaJJ.repository;

import edu.egg.AgendaJJ.entity.CourtUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourtUserRepository extends JpaRepository<CourtUser, Integer>{
    

    // Creación de consulta a partir del nombre de método
    Optional<CourtUser> findByMail(String mail);
        
    // Creación de consulta a partir del nombre de método
    boolean existsByMail(String mail);
    
    @Modifying
    @Query("UPDATE CourtUser u SET u.status = true WHERE u.id = :id")
    void enable(@Param("id") Integer id);
}