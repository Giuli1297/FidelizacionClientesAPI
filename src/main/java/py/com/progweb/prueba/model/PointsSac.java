package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
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

    @Column(name = "estado")
    private String state;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    @JsonBackReference(value = "bolsa-cliente")
    private Client client;

    @OneToMany(mappedBy = "pointsSac", cascade = {CascadeType.ALL})
    @JsonManagedReference(value="detalle-bolsa")
    private List<UseDetail> useDetailList = null;

    @OneToOne(mappedBy = "pointsSac", cascade = {CascadeType.ALL})
    @JsonManagedReference("vencimiento-bolsa")
    private PointsSacExpiration pointsSacExpiration;


    @PrePersist
    void datesAndBalance(){
        Date today = new Date();
        this.assignDate = today;

        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.HOUR, 48);
        Date expDate = c.getTime();

        this.expirationDate = expDate;
        if(this.usedPoints==null){
            this.usedPoints=0L;
        }
        this.balance = this.assignedPoints - this.usedPoints;
        this.state = "no-vencido";
    }

}
