package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.model.Client;
import py.com.progweb.prueba.model.PointsSac;
import py.com.progweb.prueba.model.PointsSacExpiration;
import py.com.progweb.prueba.persistence.PointsSacDAO;
import py.com.progweb.prueba.persistence.PointsSacExpirationDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URISyntaxException;
import java.util.List;

@Path("vencimiento")
@Consumes("application/json")
@Produces("application/json")
@Stateless
public class PointsSacExpirationRest {
    @Inject
    private PointsSacExpirationDAO pointsSacExpirationDAO;

    @Inject
    private PointsSacDAO pointsSacDAO;

    @GET
    @Path("/")
    public Response listar(){
        List<PointsSacExpiration> result = pointsSacExpirationDAO.getPointsSacExpirations();
        return Response.ok(result).build();
    }

    @GET
    @Path("/{assignId}")
    public Response getPointsSacExpiration(@PathParam("assignId") Long assignId){
        PointsSacExpiration pointsSacExpiration1 = this.pointsSacExpirationDAO.getPointsSacExpiration(assignId);
        if(pointsSacExpiration1 == null){
            return Response.noContent().build();
        }
        return Response.ok(pointsSacExpiration1).build();
    }

    @PUT
    @Path("/")
    public Response crear(@QueryParam("bolsaId") Long id, PointsSacExpiration pointsSacExpiration) throws URISyntaxException {
        PointsSac pointsSac = this.pointsSacDAO.getPointsSac(id);
        if(pointsSac == null){
            return Response.status(404).build();
        }
        pointsSacExpiration.setPointsSac(pointsSac);
        Long pid = pointsSacExpirationDAO.createPointsSacExpiration(pointsSacExpiration);
        return Response.created(UriBuilder
                .fromResource(PointsSacRest.class)
                .path("/{id}")
                .build(pid)).build();
    }

    @POST
    @Path("/update")
    public Response update(PointsSacExpiration pointsSacExpiration){
        this.pointsSacExpirationDAO.updatePointsSacExpiration(pointsSacExpiration);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id){
        this.pointsSacExpirationDAO
                .deletePointsSacExpiration(pointsSacExpirationDAO.getPointsSacExpiration(id));
        return Response.ok().build();
    }
}
