package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    @Temporal(TemporalType.DATE)
    private Date useDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    @JsonBackReference(value = "usodepuntos-cliente")
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "concepto_uso_id")
    @JsonBackReference(value = "usodepuntos-conceptodeuso")
    private UseConcept useConcept;

    @OneToMany(mappedBy = "pointsUse", cascade = {CascadeType.ALL})
    @JsonManagedReference(value = "detalle-usodepuntos")
    private List<UseDetail> useDetailList = null;

    @PrePersist
    void Dates(){
        this.useDate = new Date();
    }
}
