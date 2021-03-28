package py.com.progweb.prueba.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "regla_de_asignacion")
public class AssignRule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_asignacion")
    private Long assignRuleId;

    @Column(name = "limite_inferior")
    @Basic(optional = false)
    private Double limInf;

    @Column(name = "limite_superior")
    private Double limSup;

    @Column(name = "monto_de_equivalencia")
    @Basic(optional = false)
    private Double eqAmount;

}
