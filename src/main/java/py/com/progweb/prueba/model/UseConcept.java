package py.com.progweb.prueba.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "concepto_uso")
public class UseConcept {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "concepto_uso_id")
    private Long useConceptId;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "puntos_requeridos")
    private Long requiredPoints;

    @OneToMany(mappedBy = "useConcept")
    private List<PointsUse> pointsUseList;
}
