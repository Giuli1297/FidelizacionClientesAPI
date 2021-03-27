package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "detalle_uso")
public class UseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "detalle_uso_id")
    private Long useDetailId;

    @Column(name = "puntos_usados")
    private Long usedPoints;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bolsa_id")
    @JsonBackReference(value = "detalle-bolsa")
    private PointsSac pointsSac;

    @ManyToOne(optional = false)
    @JoinColumn(name = "uso_puntos_id")
    @JsonBackReference(value = "detalle-usodepuntos")
    private PointsUse pointsUse;
}
