package app.api.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import app.api.service.ServiceAbstract;
import app.db.controllers.AbstractDatabaseController;
import app.db.entities.BasicEntity;

public abstract class AbstractApiController<T extends BasicEntity, DBC extends AbstractDatabaseController<T>> {

	private ServiceAbstract<T, AbstractDatabaseController<T>> service;
	
	@GET
    @Produces("text/json")
	@Path("/getAll")
	public Response getAll(){
		return this.service.getAll();
	}
    
    @POST
    @Consumes("application/json")
    public Response add(T object){
    	return this.service.add(object);
    }
    
    @GET
    @Path("/{id}")
    @Produces("text/json")
	public Response getById(@PathParam("id") int id){
		return this.service.getById(id);
	}
    
    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") int id){
    	return this.service.removeById(id);
    }
    
    @PUT
    @Consumes("application/json")
    public Response update(T object) {
    	return this.service.update(object);
    }
}
