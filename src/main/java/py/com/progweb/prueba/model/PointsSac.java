package py.com.progweb.prueba.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="bolsa_puntos")
public class PointsSac {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bolsa_id")
    private Long sacId;

    @Column(name = "fecha_asignacion")
    private Date assignDate;

    @Column(name = "fecha_vencimiento")
    private Date expirationDate;

    @Basic(optional = false)
    @Column(name = "puntos_asignados")
    private Long assignedPoints;

    @Column(name = "puntos_usados")
    private Long usedPoints;

    @Column(name = "saldo")
    private Long balance;

    @Column(name = "monto_operacion")
    private Double purchaseAmount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Client client;

    @OneToMany(mappedBy = "pointsSac")
    private List<UseDetail> useDetailList;

    @PrePersist
    void Dates(){
        Date today = new Date();
        this.assignDate = today;

        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, 2);
        Date expDate = c.getTime();

        this.expirationDate = expDate;
    }

}
