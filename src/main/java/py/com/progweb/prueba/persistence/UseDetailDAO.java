package py.com.progweb.prueba.persistence;

import py.com.progweb.prueba.model.UseDetail;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class UseDetailDAO {
    @PersistenceContext(unitName = "pparcial")
    private EntityManager entityManager;

    public void createUseDetail(UseDetail useDetail){
        entityManager.persist(useDetail);
    }

    public UseDetail getUseDetail(Long id){
        return entityManager.find(UseDetail.class, id);
    }

    public List<UseDetail> getUseDetails(){
        Query q = this.entityManager.createQuery("select p from UseDetail p");
        return (List<UseDetail>) q.getResultList();
    }

    public void updateUseDetail(UseDetail useDetail){
        if(entityManager.find(UseDetail.class, useDetail.getUseDetailId()) != null){
            entityManager.merge(useDetail);
        }
    }

    public void deleteUseDetail(UseDetail useDetail){
        entityManager.remove(useDetail);
    }
}
