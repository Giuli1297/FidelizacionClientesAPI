package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.progweb.prueba.persistence.PointsSacExpirationDAO;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@Table(name = "VencimientoDePuntos")
public class PointsSacExpiration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vencimiento_id")
    private Long pointsSacExpirationId;

    @Column(name = "fecha_de_inicio")
    private Date initDate;

    @Column(name = "fecha_fin_validez")
    private Date expirationDate;

    @Column(name = "duracion_dias")
    private Long dayDuration;

    @OneToOne(optional = false, cascade = { CascadeType.REMOVE })
    @JoinColumn(name = "bolsa_puntos_id")
    @JsonBackReference("vencimiento-bolsa")
    private PointsSac pointsSac;

    public PointsSacExpiration(Date initDate, Date expirationDate, PointsSac pointsSac){
        this.initDate=initDate;
        this.expirationDate=expirationDate;
        this.pointsSac=pointsSac;
    }

    @PrePersist
    void days(){
        Date current = new Date();
        this.dayDuration = (Long) ((expirationDate.getTime() - current.getTime()) / (1000 * 60 * 60 * 24));
    }
}
