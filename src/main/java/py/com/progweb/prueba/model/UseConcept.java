package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
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
    @JsonManagedReference(value = "usodepuntos-conceptodeuso")
    private List<PointsUse> pointsUseList = null;
}
