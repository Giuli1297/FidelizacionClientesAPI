package py.com.progweb.prueba.persistence;

import py.com.progweb.prueba.model.UseConcept;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class UseConceptDAO {
    @PersistenceContext(unitName = "pparcial")
    private EntityManager entityManager;

    public Long createUseConcept(UseConcept useConcept){
        entityManager.persist(useConcept);
        return useConcept.getUseConceptId();
    }

    public UseConcept getUseConcept(Long id){
        return entityManager.find(UseConcept.class, id);
    }

    public List<UseConcept> getUseConcepts(){
        Query q = this.entityManager.createQuery("select p from UseConcept p");
        return (List<UseConcept>) q.getResultList();
    }

    public void updateUseConcept(UseConcept useConcept){
        if(entityManager.find(UseConcept.class, useConcept.getUseConceptId()) != null){
            entityManager.merge(useConcept);
        }
    }

    public void deleteUseConcept(UseConcept useConcept){
        entityManager.remove(useConcept);
    }
}
