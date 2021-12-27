package edu.egg.AgendaJJ.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Lawsuit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String caseTitle;

    @ManyToOne
    private Court court;

    public Lawsuit(Integer id, String number, String caseTitle, Court court) {
        this.id = id;
        this.number = number;
        this.caseTitle = caseTitle;
        this.court = court;
    }

    public Lawsuit() {
    }
}