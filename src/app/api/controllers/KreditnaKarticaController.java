package app.api.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import app.api.service.KreditnaKarticaService;
import app.db.controllers.KreditnaKarticaDbController;
import app.db.entities.KreditnaKartica;

@Path("/KreditnaKartica")
public class KreditnaKarticaController extends AbstractApiController<KreditnaKartica, KreditnaKarticaDbController>{

	private KreditnaKarticaService service;

	public KreditnaKarticaController() {
		this.service = new KreditnaKarticaService(new KreditnaKarticaDbController());
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/getAll")
	public Response getAll(){
		return this.service.getAll();
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/getForUser")
	public Response getForUser(@QueryParam("id") int id){
		return this.service.getForUser(id);
	}
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response add(KreditnaKartica object){
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
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response update(KreditnaKartica object) {
    	return this.service.update(object);
    }
}
