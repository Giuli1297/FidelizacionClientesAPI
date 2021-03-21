package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.Agenda;
import py.com.progweb.prueba.model.Persona;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class PersonaDAO {
    @PersistenceContext(unitName = "bdpwb")
    private EntityManager em;

    @Inject
    AgendaDAO agendaDAO;

    public void agregar(Persona entidad){

        this.em.persist(entidad);
        for (Agenda a: entidad.getListaAgendas()){
            a.setPersona(entidad);
            agendaDAO.agregar(a);
        }
    }

    public List<Persona> lista(){
        Query query = this.em.createQuery("select p from Persona p");
        return (List<Persona>) query.getResultList();
    }
}
