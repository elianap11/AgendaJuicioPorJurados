package edu.egg.AgendaJJ.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
public class Court {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String courtPresident;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Long phoneNumber;

    @ManyToOne
    private JudicialDivision judicialDivision;

    public Court(Integer id, String name, String courtPresident, String address, Long phoneNumber, JudicialDivision judicialDivision) {
        this.id = id;
        this.name = name;
        this.courtPresident = courtPresident;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.judicialDivision = judicialDivision;
    }

    public Court() {
    }
}
