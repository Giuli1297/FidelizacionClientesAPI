package py.com.progweb.prueba.persistence;

import py.com.progweb.prueba.model.PointsUse;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class PointsUseDAO {
    @PersistenceContext(unitName = "pparcial")
    private EntityManager entityManager;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Long createPointsUse(PointsUse pointsUse){
        entityManager.persist(pointsUse);
        return pointsUse.getPointsUseId();
    }

    public PointsUse getPointsUse(Long id){
        return entityManager.find(PointsUse.class, id);
    }

    public List<PointsUse> getPointsUses(){
        Query q = this.entityManager.createQuery("select p from PointsUse p");
        return (List<PointsUse>) q.getResultList();
    }

    public void updatePointsUse(PointsUse pointsUse){
        if(entityManager.find(PointsUse.class, pointsUse.getPointsUseId()) != null){
            entityManager.merge(pointsUse);
        }
    }

    public void deletePointsUse(PointsUse pointsUse){
        entityManager.remove(pointsUse);
    }
}
