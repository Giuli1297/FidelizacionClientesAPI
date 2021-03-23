package py.com.progweb.prueba.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "cliente")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cliente_id")
    private Long clientId;

    @Column(name = "nombre")
    private String name;

    @Column(name = "apellido")
    private String lastName;

    @Column(name = "numero_documento")
    private Long documentNumber;

    @Column(name = "tipo_documento")
    private String documentType;

    @Column(name = "nacionalidad")
    private String nationality;

    @Column(name = "email")
    private String email;

    @Column(name = "telefono")
    private String phone;

    @Column(name = "fecha_de_nacimiento")
    private Date birthday;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<PointsSac> pointsSacList;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<PointsUse> pointsUseList;

}
