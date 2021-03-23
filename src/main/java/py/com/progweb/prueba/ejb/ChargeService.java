package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.PointsSac;
import py.com.progweb.prueba.persistence.PointsSacDAO;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ChargeService {
    @Inject
    private PointsSacDAO pSacDao;

    public void createPointsSac(PointsSac pSac){
        pSacDao.addPSac(pSac);
    }

}

