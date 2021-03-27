package py.com.progweb.prueba.rest;

import org.jboss.logging.annotations.Pos;
import py.com.progweb.prueba.model.Client;
import py.com.progweb.prueba.persistence.ClientDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("cliente")
@Consumes("application/json")
@Produces("application/json")
@Stateless
public class ClientRest {
    @Inject
    private ClientDAO clientDAO;

    @GET
    @Path("/")
    public Response listar(){
        List<Client> result = clientDAO.getClients();
        return Response.ok(result).build();
    }

    @GET
    @Path("/{clientId}")
    public Response getClient(@PathParam("clientId") Long clientId){
        Client client = this.clientDAO.getClient(clientId);
        if(client == null){
            return Response.noContent().build();
        }
        return Response.ok(client).build();
    }

    @PUT
    @Path("/")
    public Response crear(Client client) throws URISyntaxException {
        Long id = this.clientDAO.createClient(client);
        return Response.created(UriBuilder
                .fromResource(ClientRest.class)
                .path("/{id}")
                .build(id)).build();
    }

    @POST
    @Path("/update/{clientId}")
    public Response updateClient(Client client){
        this.clientDAO.updateClient(client);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{clientId}")
    public Response deleteClient(@PathParam("clientId") Long clientId){
        this.clientDAO.deleteClient(this.clientDAO.getClient(clientId));
        return Response.ok().build();
    }

}
