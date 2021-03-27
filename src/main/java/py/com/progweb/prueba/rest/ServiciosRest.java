package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.model.PointsSac;
import py.com.progweb.prueba.model.PointsUse;
import py.com.progweb.prueba.model.UseConcept;
import py.com.progweb.prueba.model.UseDetail;
import py.com.progweb.prueba.persistence.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.ResourceBundle;

@Path("servicios")
@Consumes("application/json")
@Produces("application/json")
@Stateless
public class ServiciosRest {
    @Inject
    private ClientDAO clientDAO;

    @Inject
    private PointsSacDAO pointsSacDAO;

    @Inject
    private UseConceptDAO useConceptDAO;

    @Inject
    private PointsUseDAO pointsUseDAO;

    @Inject
    private UseDetailDAO useDetailDAO;

    @POST
    @Path("/cargar")
    public Response cargar(@QueryParam("clienteId") Long clienteId, @QueryParam("montoDeLaOperacion") Double amount){
        PointsSac pointsSac = new PointsSac();
        pointsSac.setClient(this.clientDAO.getClient(clienteId));
        pointsSac.setAssignedPoints(pointsSacDAO.calculatePoints(amount));
        pointsSac.setPurchaseAmount(amount);
        Long id = pointsSacDAO.addPSac(pointsSac);
        return Response.ok(pointsSacDAO.getPointsSac(id)).build();
    }

    @POST
    @Path("/canjear")
    public Response canjear(@QueryParam("clienteId") Long clienteId, @QueryParam("conceptoId") Long conceptoId){
        Long pointsCounter = 0L;
        //Obtener cantidad de puntos requeridos
        Long requiredPoints = useConceptDAO.getUseConcept(conceptoId).getRequiredPoints();
        //Obtener una lista ordenada por fecha ascendente de bolsas de puntos del cliente
        List<PointsSac> pointsSacList = pointsSacDAO.listWhereClienteOrderByDate(clienteId);
        //Descontar los puntos necesarios de las bolsas en orde secuencial y crear un uso de puntos
        for(PointsSac pointsSac : pointsSacList){
            pointsCounter =+ pointsSac.getBalance();
            if(pointsCounter >= requiredPoints){
                break;
            }
        }
        if(pointsCounter < requiredPoints){
            return Response.notModified(""+pointsCounter+" "+requiredPoints).build();
        }
        PointsUse pointsUse = new PointsUse();
        pointsUse.setUsedPoints(requiredPoints);
        pointsUse.setClient(clientDAO.getClient(clienteId));
        pointsUse.setUseConcept(useConceptDAO.getUseConcept(conceptoId));
        Long pointsUseId = pointsUseDAO.createPointsUse(pointsUse);
        for(PointsSac pointsSac : pointsSacList){
            Long avaiblePoints = pointsSac.getBalance();
            if(avaiblePoints<=0){
                continue;
            }
            if(avaiblePoints<requiredPoints){
                System.out.println("rp "+requiredPoints+" ba "+avaiblePoints);
                requiredPoints = requiredPoints - avaiblePoints;
                System.out.println("rp "+requiredPoints+" ba "+avaiblePoints);
                UseDetail detail = new UseDetail();
                detail.setPointsSac(pointsSac);
                detail.setUsedPoints(avaiblePoints);
                detail.setPointsUse(pointsUseDAO.getPointsUse(pointsUseId));
                useDetailDAO.createUseDetail(detail);
                pointsSac.setBalance(0L);
                pointsSac.setUsedPoints(pointsSac.getAssignedPoints());
                pointsSacDAO.updatePointsSac(pointsSac);
            }else{
                System.out.println("rp "+requiredPoints+" ba "+avaiblePoints);
                UseDetail detail = new UseDetail();
                detail.setPointsSac(pointsSac);
                detail.setUsedPoints(requiredPoints);
                detail.setPointsUse(pointsUseDAO.getPointsUse(pointsUseId));
                useDetailDAO.createUseDetail(detail);
                pointsSac.setBalance(avaiblePoints - requiredPoints);
                pointsSac.setUsedPoints(pointsSac.getUsedPoints() + requiredPoints);
                pointsSacDAO.updatePointsSac(pointsSac);
                break;
            }
        }
        return Response.ok(pointsUseDAO.getPointsUse(pointsUseId)).build();
    }

    /*public Long calculatePoints(Double operationAmount){
        long points;
        if(operationAmount <= 199999){
            operationAmount = operationAmount / 50000;
            points = operationAmount.longValue();
        }else if(operationAmount <= 499999){
            operationAmount = operationAmount / 30000;
            points = operationAmount.longValue();
        }else{
            operationAmount = operationAmount / 20000;
            points = operationAmount.longValue();
        }
        return points;
    }*/
}
