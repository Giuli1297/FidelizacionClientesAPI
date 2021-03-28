package py.com.progweb.prueba.persistence;

import py.com.progweb.prueba.model.AssignRule;
import py.com.progweb.prueba.model.PointsSac;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class AssignRuleDAO {
    @PersistenceContext(unitName = "pparcial")
    private EntityManager entityManager;

    public Long createAssignRule(AssignRule assignRule){
        if(assignRule.getEqAmount()<= 0){
            return null;
        }

        Query q = this.entityManager.createQuery("select a from AssignRule a");
        List<AssignRule> assignRules = (List<AssignRule>) q.getResultList();
        int b=1;
        int b2=1;
        AssignRule assignRule2=null;
        for(AssignRule assignRule1 : assignRules){
            if(assignRule.getLimInf()<assignRule1.getLimInf()){
                b=0;
                break;
            }
            if(assignRule1.getLimSup()==0.0){
                b2=2;
                assignRule2=assignRule1;
            }
        }
        if(b==1 && b2==2){
            assignRule2.setLimSup(assignRule.getLimInf()-1);
            assignRule.setLimSup(0.0);
            entityManager.merge(assignRule2);
            entityManager.persist(assignRule);
            return assignRule.getAssignRuleId();
        }else if(b==1){
         assignRule.setLimSup(0.0);
         entityManager.persist(assignRule);
        }else{
            return null;
        }
        return assignRule.getAssignRuleId();
    }

    public AssignRule getAssignRule(Long id){
        return entityManager.find(AssignRule.class, id);
    }

    public List<AssignRule> getAssignRules(){
        Query q = this.entityManager.createQuery("select p from AssignRule p order by p.limInf DESC");
        return (List<AssignRule>) q.getResultList();
    }

    public Long updateAssignRule(AssignRule assignRule){
        if(assignRule.getEqAmount()<=0){
            return null;
        }
        Query q = entityManager.createQuery("select a from AssignRule a where a.assignRuleId=:id")
                .setParameter("id", assignRule.getAssignRuleId());
        AssignRule assignRule1 = (AssignRule) q.getSingleResult();
        assignRule1.setEqAmount(assignRule.getEqAmount());
        entityManager.merge(assignRule1);
        return 1L;
    }

    public Long deleteAssignRule(){
        Query q = entityManager.createQuery("select a from AssignRule a where a.limSup=0");
        AssignRule assignRule1 = (AssignRule) q.getSingleResult();
        Query q1 = entityManager.createQuery("select a from AssignRule a where a.limSup=:linf")
                .setParameter("linf", assignRule1.getLimInf()-1);
        AssignRule assignRule2 = (AssignRule) q1.getSingleResult();
        if(assignRule1==null && assignRule2==null){
            return null;
        }else if(assignRule2==null){
            entityManager.remove(assignRule1);
        }else{
            assignRule2.setLimSup(0.0);
            entityManager.merge(assignRule2);
            entityManager.remove(assignRule1);
        }
        return 1L;
    }
    
}
