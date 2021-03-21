package py.com.progweb.prueba.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name="Agenda")
public class Agenda {

    @Id
    @Column(name = "id_agenda")
    @Basic(optional = false)
    @GeneratedValue(generator = "agendaSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "agendaSec", sequenceName = "agenda_sec", allocationSize = 0)
    private Integer idAgenda;

    @Column(name = "actividad", length = 200)
    @Basic(optional = false)
    private String actividad;

    @Column(name = "fecha")
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    @ManyToOne(optional = false)
    private Persona persona;

}
