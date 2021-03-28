package py.com.progweb.prueba.persistence;

import py.com.progweb.prueba.model.AssignRule;
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

    @Inject
    private AssignRuleDAO assignRuleDAO;


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

    public List<PointsSac> listWhereClienteOrderByDate(Long client){
        Query q = this.entityManager
                .createQuery("select c from PointsSac c where c.client.clientId = :client and c.balance > 0 and c.state = 'no-vencido' ORDER BY c.assignDate")
                .setParameter("client", client);
        return (List<PointsSac>) q.getResultList();
    }

    public List<PointsSac> listByClientAndRange(Long clientId, Long infRange, Long supRange){
        Query q = this.entityManager
                .createQuery("SELECT s from PointsSac s WHERE s.client.clientId=:clientId and s.balance BETWEEN :inf and :sup")
                .setParameter("clientId", clientId)
                .setParameter("inf", infRange)
                .setParameter("sup", supRange);
        return (List<PointsSac>) q.getResultList();
    }

    public List<PointsSac> listByDaysForExpiration(Long days){
        Query q = this.entityManager
                .createQuery("SELECT s from PointsSac s WHERE s.pointsSacExpiration.dayDuration = :days")
                .setParameter("days", days);
        return (List<PointsSac>) q.getResultList();
    }

    public void deletePointsSac(PointsSac pointsSac){
        entityManager.remove(pointsSac);
    }

    public Long calculatePoints(Double operationAmount) {
        List<AssignRule> assignRules = assignRuleDAO.getAssignRules();
        long points = 0;
        if(assignRules.isEmpty()){
            //default
            if (operationAmount <= 199999) {
                operationAmount = operationAmount / 50000;
                points = operationAmount.longValue();
            } else if (operationAmount <= 499999) {
                operationAmount = operationAmount / 30000;
                points = operationAmount.longValue();
            } else {
                operationAmount = operationAmount / 20000;
                points = operationAmount.longValue();
            }
        }else{
            for(AssignRule assignRule: assignRules){
                if(operationAmount > assignRule.getLimInf()){
                    operationAmount = operationAmount / assignRule.getEqAmount();
                    points = operationAmount.longValue();
                    break;
                }
            }
        }
        return points;
    }
}

