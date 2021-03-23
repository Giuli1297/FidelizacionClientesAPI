package py.com.progweb.prueba.model;

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

    @ManyToOne
    @JoinColumn(name = "bolsa_id")
    private PointsSac pointsSac;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "uso_puntos_id")
    private PointsUse pointsUse;
}
