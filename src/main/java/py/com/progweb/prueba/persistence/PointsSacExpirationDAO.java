package py.com.progweb.prueba.persistence;

import py.com.progweb.prueba.model.PointsSacExpiration;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class PointsSacExpirationDAO {
    @PersistenceContext(unitName = "pparcial")
    private EntityManager entityManager;

    public Long createPointsSacExpiration(PointsSacExpiration pointsSacExpiration){
        entityManager.persist(pointsSacExpiration);
        return pointsSacExpiration.getPointsSacExpirationId();
    }

    public PointsSacExpiration getPointsSacExpiration(Long id){
        return entityManager.find(PointsSacExpiration.class, id);
    }

    public List<PointsSacExpiration> getPointsSacExpirations(){
        Query q = this.entityManager.createQuery("select p from PointsSacExpiration p");
        return (List<PointsSacExpiration>) q.getResultList();
    }

    public void updatePointsSacExpiration(PointsSacExpiration pointsSacExpiration){
        if(entityManager.find(PointsSacExpiration.class, pointsSacExpiration.getPointsSacExpirationId()) != null){
            entityManager.merge(pointsSacExpiration);
        }
    }

    public void deletePointsSacExpiration(PointsSacExpiration pointsSacExpiration){
        entityManager.remove(pointsSacExpiration);
    }
}
