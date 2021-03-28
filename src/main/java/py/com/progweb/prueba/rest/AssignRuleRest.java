package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.model.AssignRule;
import py.com.progweb.prueba.model.Client;
import py.com.progweb.prueba.persistence.AssignRuleDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URISyntaxException;
import java.util.List;

@Path("regla-de-asignacion")
@Consumes("application/json")
@Produces("application/json")
@Stateless
public class AssignRuleRest {
    @Inject
    private AssignRuleDAO assignRuleDAO;

    @GET
    @Path("/")
    public Response listar(){
        List<AssignRule> result = assignRuleDAO.getAssignRules();
        return Response.ok(result).build();
    }

    @GET
    @Path("/{assignId}")
    public Response getAssignRule(@PathParam("assignId") Long assignId){
        AssignRule assignRule1 = this.assignRuleDAO.getAssignRule(assignId);
        if(assignRule1 == null){
            return Response.noContent().build();
        }
        return Response.ok(assignRule1).build();
    }

    @PUT
    @Path("/")
    public Response crear(AssignRule assignRule) throws URISyntaxException {
        Long id = this.assignRuleDAO.createAssignRule(assignRule);
        if(id==null){
            return Response.status(400).build();
        }
        return Response.created(UriBuilder
                .fromResource(AssignRuleRest.class)
                .path("/{id}")
                .build(id)).build();
    }

    @POST
    @Path("/update")
    public Response updateClient(AssignRule assignRule){
        Long state = this.assignRuleDAO.updateAssignRule(assignRule);
        if(state==null){
            return Response.status(400).build();
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("/eliminarSup")
    public Response deleteClient(){
        Long state = this.assignRuleDAO.deleteAssignRule();
        if(state==null){
            return Response.status(404).build();
        }
        return Response.ok().build();
    }
}
