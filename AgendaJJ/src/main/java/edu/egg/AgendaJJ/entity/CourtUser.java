package edu.egg.AgendaJJ.entity;

import edu.egg.AgendaJJ.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import org.hibernate.annotations.SQLDelete;

@Entity
@Setter
@Getter
@SQLDelete(sql = "UPDATE CourtUser SET status = false WHERE id = ?")
public class CourtUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private Long dni;

    @Column(nullable = false, unique = true)
    private String mail;

    @Column(nullable = false, unique = true)
    private String password;
    
    private String jobTitle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;
    
    //AGREGO IMAGEN
    private String image;

    private Boolean status;

    public CourtUser(Integer id, String name, Long dni, String mail, String password, String jobTitle, UserRole userRole, String image) {
        this.id = id;
        this.name = name;
        this.dni = dni;
        this.mail = mail;
        this.password = password;
        this.jobTitle = jobTitle;
        this.userRole = userRole;
        this.image = image;
        this.status = true;
    }

    public CourtUser() {
    }

}
