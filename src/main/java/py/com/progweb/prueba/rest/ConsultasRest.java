package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.model.Client;
import py.com.progweb.prueba.model.PointsSac;
import py.com.progweb.prueba.model.PointsUse;
import py.com.progweb.prueba.model.UseConcept;
import py.com.progweb.prueba.persistence.ClientDAO;
import py.com.progweb.prueba.persistence.PointsSacDAO;
import py.com.progweb.prueba.persistence.PointsUseDAO;
import py.com.progweb.prueba.persistence.UseConceptDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

@Path("consultas")
@Consumes("application/json")
@Produces("application/json")
@Stateless
public class ConsultasRest {
    @Inject
    private PointsUseDAO pointsUseDAO;

    @Inject
    private PointsSacDAO pointsSacDAO;

    @Inject
    private ClientDAO clientDAO;

    @Inject
    private UseConceptDAO useConceptDAO;

    @GET
    @Path("/listarUsosDePuntos")
    public Response listarUsoDePuntos(
            @QueryParam("conceptoDeUso") Long idConcepto,
            @QueryParam("fechaDeUso") String fechaUso,
            @QueryParam("cliente") Long idCliente) {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date fecha = null;
        try {
            fecha = myFormat.parse(fechaUso);
        } catch (ParseException e) {
            return Response.status(400).build();
        }
        List<PointsUse> pointsUseList = pointsUseDAO.listByConceptAndDateAndClient(idConcepto, fecha, idCliente);
        return Response.ok(pointsUseList).build();
    }

    @GET
    @Path("/listarBolsaDePuntos")
    public Response listarBolsaDePuntos(
            @QueryParam("cliente") Long clientId,
            @QueryParam("puntosRangoInf") Long rangInf,
            @QueryParam("puntosRangoSup") Long rangSup){
        if(rangInf > rangSup){
            return Response.status(400).build();
        }
        List<PointsSac> pointsSacList = pointsSacDAO.listByClientAndRange(clientId, rangInf, rangSup);
        return Response.ok(pointsSacList).build();
    }

    @GET
    @Path("/listarClientesConPuntosAVencer")
    public Response listarClientesPuntosAVencer(@QueryParam("xDias") Long xDias){
        if(xDias<0){
            return Response.status(400).build();
        }
        List<PointsSac> pointsSacList = pointsSacDAO.listByDaysForExpiration(xDias);
        List<Client> clients = new ArrayList<Client>();
        for(PointsSac pointsSac : pointsSacList){
            Client x = pointsSac.getClient();
            if(!clients.contains(x)){
                clients.add(x);
            }
        }
        return Response.ok(clients).build();
    }

    @GET
    @Path("/listarClientesPorNombreApellidoNacimiento")
    public Response listarClientesNombreApellidoNacimiento(
            @QueryParam("nombre") String name,
            @QueryParam("apellido") String lastname,
            @QueryParam("nacimiento") String birthday){
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date fecha = null;
        try {
            fecha = myFormat.parse(birthday);
        } catch (ParseException e) {
            return Response.status(400).build();
        }
        List<Client> clients = clientDAO.listByNameOrLastnameOrBirthday(name, lastname, fecha);
        return Response.ok(clients).build();
    }

    @GET
    @Path("calcularPuntosPorMonto")
    public Response puntosXMonto(@QueryParam("monto") Double amount){
        return Response.ok(pointsSacDAO.calculatePoints(amount)).build();
    }
}
