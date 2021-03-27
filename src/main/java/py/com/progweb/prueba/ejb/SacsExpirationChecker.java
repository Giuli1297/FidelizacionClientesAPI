package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.PointsSac;
import py.com.progweb.prueba.model.PointsSacExpiration;
import py.com.progweb.prueba.persistence.PointsSacDAO;
import py.com.progweb.prueba.persistence.PointsSacExpirationDAO;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Singleton
@Startup
public class SacsExpirationChecker {

    @Inject
    private PointsSacDAO pointsSacDAO;

    @Inject
    private PointsSacExpirationDAO pointsSacExpirationDAO;

    @Schedule(hour = "*/1")
    public void actualizeDatesAndDeletedExpiredSacs(){
        List<PointsSacExpiration> pointsSacExpirations = pointsSacExpirationDAO.getPointsSacExpirations();
        for(PointsSacExpiration pointsSacExpiration: pointsSacExpirations) {
            Date current = new Date();
            long difference = pointsSacExpiration.getExpirationDate().getTime() - current.getTime();
            Long daysBetween = (Long) (difference / (1000 * 60 * 60 * 24));
            if (difference<0) {
                pointsSacExpirationDAO.deletePointsSacExpiration(pointsSacExpiration);
                System.out.println("Se a eliminado la bolsa de puntos con id "
                        + pointsSacExpiration.getPointsSacExpirationId()+" por vencimiento");
            } else {
                pointsSacExpiration.setDayDuration(daysBetween);
                pointsSacExpirationDAO.updatePointsSacExpiration(pointsSacExpiration);
            }
        }
    }
}
