package py.com.progweb.prueba.persistence;

import py.com.progweb.prueba.model.Client;
import py.com.progweb.prueba.model.PointsSac;
import py.com.progweb.prueba.model.PointsSacExpiration;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class PointsSacDAO {
    @PersistenceContext(unitName = "pparcial")
    private EntityManager entityManager;

    @Inject
    private PointsSacExpirationDAO pointsSacExpirationDAO;


    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Long addPSac(PointsSac pSac){
        entityManager.persist(pSac);
        pointsSacExpirationDAO.createPointsSacExpiration(
                new PointsSacExpiration(pSac.getAssignDate(), pSac.getExpirationDate(), pSac));
        return pSac.getSacId();
    }

    public PointsSac getPointsSac(Long id){
        return entityManager.find(PointsSac.class, id);
    }

    public List<PointsSac> getPointsSacs(){
        Query q = this.entityManager.createQuery("select p from PointsSac p");
        return (List<PointsSac>) q.getResultList();
    }

    public void updatePointsSac(PointsSac pointsSac){
        if(entityManager.find(PointsSac.class, pointsSac.getSacId()) != null){
            entityManager.merge(pointsSac);
        }
    }

    public List<PointsSac> customQuery(Long client){
        Query q = this.entityManager.createQuery("select c from PointsSac c where c.client.clientId = :client ORDER BY c.assignDate")
                .setParameter("client", client);
        return (List<PointsSac>) q.getResultList();
    }

    public void deletePointsSac(PointsSac pointsSac){
        entityManager.remove(pointsSac);
    }

}

