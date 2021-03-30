package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.Nullable;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
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
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @OneToMany(mappedBy = "client", cascade = {CascadeType.ALL})
    @JsonManagedReference(value="bolsa-cliente")
    private List<PointsSac> pointsSacList = null;

    @OneToMany(mappedBy = "client", cascade = {CascadeType.ALL})
    @JsonManagedReference(value = "usodepuntos-cliente")
    private List<PointsUse> pointsUseList = null;

}
