package py.com.progweb.prueba.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "uso_puntos")
public class PointsUse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uso_puntos_id")
    private Long pointsUseId;

    @Column(name = "puntos_usados")
    private Long usedPoints;

    @Column(name = "fecha_uso")
    private Date useDate;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "concepto_uso_id")
    private UseConcept useConcept;

    @PrePersist
    void Dates(){
        this.useDate = new Date();
    }
}
