package py.com.progweb.prueba.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "persona")
public class Persona {
    @Id
    @Column(name = "id_persona")
    @Basic(optional = false)
    @GeneratedValue(generator = "personaSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "personaSec", sequenceName = "persona_sec", allocationSize = 0)
    private Integer idPersona;

    @Column(name = "nombre", length = 50)
    @Basic(optional = false)
    private String nombre;

    @Column(name = "apellido", length = 50)
    @Basic(optional = false)
    private String apellido;

    @OneToMany(mappedBy = "persona")
    private List<Agenda> listaAgendas;


}
