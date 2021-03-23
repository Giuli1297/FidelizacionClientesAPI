package py.com.progweb.prueba.persistence;

import py.com.progweb.prueba.model.PointsSac;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class PointsSacDAO {
    @PersistenceContext(unitName = "pparcial")
    private EntityManager entityManager;

    public void addPSac(PointsSac pSac){
        entityManager.persist(pSac);
    }

    public PointsSac getPointsSac(Long id){
        return entityManager.find(PointsSac.class, id);
    }

    public void updatePointsSac(PointsSac pointsSac){
        entityManager.merge(pointsSac);
    }

    public void deletePointsSac(PointsSac pointsSac){
        entityManager.remove(pointsSac);
    }

}

