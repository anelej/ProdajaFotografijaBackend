package app.api.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import app.api.service.IstorijaKupovineService;
import app.db.controllers.IstorijaKupovineDbController;
import app.db.entities.IstorijaKupovine;

@Path("/IstorijaKupovine")
public class IstorijaKupovineController extends AbstractApiController<IstorijaKupovine, IstorijaKupovineDbController>{

	private IstorijaKupovineService service;

	public IstorijaKupovineController() {
		this.service = new IstorijaKupovineService(new IstorijaKupovineDbController());
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/getAll")
	public Response getAll(){
		return this.service.getAll();
	}
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(IstorijaKupovine object){
    	return this.service.add(object);
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
	public Response getById(@PathParam("id") int id){
		return this.service.getById(id);
	}
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response remove(@PathParam("id") int id){
    	return this.service.removeById(id);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(IstorijaKupovine object) {
    	return this.service.update(object);
    }
}