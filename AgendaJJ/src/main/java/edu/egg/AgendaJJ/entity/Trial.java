package edu.egg.AgendaJJ.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE Trial SET status = false WHERE id = ?")
public class Trial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate dateTrial;

    @OneToOne
    private Court court;

    @OneToOne
    private Lawsuit lawsuit;

    @ManyToOne
    private JudicialDivision judicialDivision;

    private Boolean status;

    public Trial(Integer id, LocalDate dateTrial, Court court, Lawsuit lawsuit, JudicialDivision judicialDivision) {
        this.id = id;
        this.dateTrial = dateTrial;
        this.court = court;
        this.lawsuit = lawsuit;
        this.judicialDivision = judicialDivision;
        this.status = true;
    }

    public Trial() {
    }

}