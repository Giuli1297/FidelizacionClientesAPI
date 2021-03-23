package py.com.progweb.prueba.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "VencimientoDePuntos")
public class PointsSacExpiration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vencimiento_id")
    private Long expiration_id;

    @Column(name = "fecha_de_inicio")
    private Date initDate;

    @Column(name = "fecha_fin_validez")
    private Date expirationDate;

    @Column(name = "duracion_dias")
    private Long dayDuration;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "bolsa_puntos_id")
    private PointsSac pointsSac;

}
