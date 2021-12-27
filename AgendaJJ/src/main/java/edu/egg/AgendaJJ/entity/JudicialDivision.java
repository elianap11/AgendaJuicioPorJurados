package edu.egg.AgendaJJ.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
public class JudicialDivision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany (mappedBy = "judicialDivision")
    private List<Trial> trials;

    public JudicialDivision(Integer id, String name, List<Trial> trials) {
        this.id = id;
        this.name = name;
        this.trials = trials;
    }

    public JudicialDivision() {
    }
}
