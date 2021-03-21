package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.Agenda;
import py.com.progweb.prueba.model.Persona;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class AgendaDAO {
    @PersistenceContext(unitName = "bdpwb")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void agregar(Agenda entidad){
        this.em.persist(entidad);
    }

    /*public List<Agenda> lista(){
        Query query = this.em.createQuery("select p from Persona p");
        return (List<Agenda>) query.getResultList();
    }*/
}
