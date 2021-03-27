package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.model.Client;
import py.com.progweb.prueba.model.PointsSac;
import py.com.progweb.prueba.model.PointsSac;
import py.com.progweb.prueba.persistence.ClientDAO;
import py.com.progweb.prueba.persistence.PointsSacDAO;
import py.com.progweb.prueba.persistence.PointsSacDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URISyntaxException;
import java.util.List;

@Stateless
@Path("bolsa-de-puntos")
@Consumes("application/json")
@Produces("application/json")
public class PointsSacRest {
    @Inject
    private PointsSacDAO pointsSacDAO;
    @Inject
    private ClientDAO clientDAO;

    @GET
    @Path("/")
    public Response listar(){
        List<PointsSac> result = pointsSacDAO.getPointsSacs();
        return Response.ok(result).build();
    }

    @GET
    @Path("/{pointsSacId}")
    public Response getPointsSac(@PathParam("pointsSacId") Long pointsSacId){
        PointsSac client = this.pointsSacDAO.getPointsSac(pointsSacId);
        if(client == null){
            return Response.noContent().build();
        }
        return Response.ok(client).build();
    }

    @PUT
    @Path("/")
    public Response crear(@QueryParam("cliente") Long clientId, PointsSac pointsSac) throws URISyntaxException {
        Client client = this.clientDAO.getClient(clientId);
        if(client == null){
            return Response.status(404).build();
        }
        pointsSac.setClient(client);
        Long id = this.pointsSacDAO.addPSac(pointsSac);
        return Response.created(UriBuilder
                .fromResource(PointsSacRest.class)
                .path("/{id}")
                .build(id)).build();
    }

    @POST
    @Path("/update")
    public Response updatePointsSac(PointsSac pointsSac){
        this.pointsSacDAO.updatePointsSac(pointsSac);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{pointsSacId}")
    public Response deletePointsSac(@PathParam("pointsSacId") Long pointsSacId){
        PointsSac pointsSac = this.pointsSacDAO.getPointsSac(pointsSacId);
        if(pointsSac == null){
            return Response.status(404).build();
        }
        this.pointsSacDAO.deletePointsSac(pointsSac);
        return Response.ok().build();
    }
}
