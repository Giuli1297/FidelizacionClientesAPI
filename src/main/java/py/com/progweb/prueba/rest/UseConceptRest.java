package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.model.Client;
import py.com.progweb.prueba.model.UseConcept;
import py.com.progweb.prueba.persistence.UseConceptDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URISyntaxException;
import java.util.List;

@Path("concepto-de-uso")
@Consumes("application/json")
@Produces("application/json")
@Stateless
public class UseConceptRest {
    @Inject
    private UseConceptDAO useConceptDAO;

    @GET
    @Path("/")
    public Response listar(){
        List<UseConcept> result = useConceptDAO.getUseConcepts();
        return Response.ok(result).build();
    }

    @GET
    @Path("/{useConceptId}")
    public Response getClient(@PathParam("useConceptId") Long id){
        UseConcept value = this.useConceptDAO.getUseConcept(id);
        if(value == null){
            return Response.noContent().build();
        }
        return Response.ok(value).build();
    }

    @PUT
    @Path("/")
    public Response crear(UseConcept useConcept) throws URISyntaxException {
        Long id = this.useConceptDAO.createUseConcept(useConcept);
        return Response.created(UriBuilder
                .fromResource(UseConceptRest.class)
                .path("/{id}")
                .build(id)).build();
    }

    @POST
    @Path("/update")
    public Response updateUseConcept(UseConcept useConcept){
        this.useConceptDAO.updateUseConcept(useConcept);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{useConceptId}")
    public Response deleteClient(@PathParam("useConceptId") Long useConceptId){
        UseConcept useConcept = this.useConceptDAO.getUseConcept(useConceptId);
        if(useConcept==null){
            return Response.status(400).build();
        }
        this.useConceptDAO.deleteUseConcept(useConcept);
        return Response.ok().build();
    }
}
