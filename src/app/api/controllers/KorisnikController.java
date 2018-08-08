package app.api.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import app.api.dto.LoginDto;
import app.api.dto.LoginResponseDto;
import app.api.dto.ResponseDto;
import app.api.service.KorisnikService;
import app.db.controllers.KorisnikDbController;
import app.db.entities.Korisnik;
import app.utils.Const;

@Path("/korisnik")
public class KorisnikController extends AbstractApiController<Korisnik, KorisnikDbController>{

	private KorisnikService service;

	public KorisnikController() {
		this.service = new KorisnikService(new KorisnikDbController());
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	public Response LoginUser(LoginDto loginDto) {
		if (loginDto == null || loginDto.getUsername() == null 
				|| loginDto.getUsername() == "" || loginDto.getPassword() == null || loginDto.getPassword()== "") {
			return Response.ok().entity(new ResponseDto(null, Const.USER_OR_PASS_EMPTY)).build();
		}
		LoginResponseDto response = service.Login(loginDto.getUsername(), loginDto.getPassword());
		if (response != null) {
			return Response.ok(response).build();
		} else {
			return Response.ok().entity(new ResponseDto(null, Const.WRONG_PASS_OR_USER)).build();
		}
	}
	
	@GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/activate/{username}")
    public Response activate(@PathParam("username") String username){
    	return this.service.activate(username);
    }
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/register")
    public Response register(Korisnik object){
    	return this.service.register(object);
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
    @Path("/add")
    public Response add(Korisnik object){
    	if (object.getUloga() == 2) {
    		object.setPromenaPasworda(true);
    	}
    	if (object.getUloga() > 1) {
    		object.setAktivan(true);
    	}
    	return this.service.add(object);
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSellers")
	public Response getSellers(){
		return this.service.getSellers();
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
    public Response update(Korisnik object) {
    	return this.service.update(object);
    }
    
    @GET
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/getUsersForTest")
	public Response getUsersForTest(){
		return this.service.getUsersForTest();
	}
    
    @GET
   	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
   	@Path("/userBecomeSeller")
   	public Response userBecomeSeller(@QueryParam("id") int id){
   		return this.service.userBecomeSeller(id);
   	}
    
    @GET
   	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
   	@Path("/userFailTest")
   	public Response userFailTest(@QueryParam("id") int id){
   		return this.service.userFailTest(id);
   	}
    
    @GET
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/getSellersOfBougthPhotos")
	public Response getSellersOfBougthPhotos(@QueryParam("id") int id){
		return this.service.getSellersOfBougthPhotos(id);
	}
    
    @GET
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/getReportForUser")
	public Response getReportForUser(@QueryParam("id") int id){
		return this.service.getReportForUser(id);
	}
    
    @GET
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/getUsersBasedOnRole")
	public Response getUsers(@QueryParam("role") int role){
		return this.service.getUsersBasedOnRole(role); 
    }
    
    @GET
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/deactivate")
	public Response deactivate(@QueryParam("id") int id){
		return this.service.deactivate(id); 
    }
    
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/changePassword")
	public Response changePassword (Korisnik korisnik){
		return this.service.changePassword(korisnik); 
    }
    
    @GET
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/resetPassword")
	public Response resetPassword(@QueryParam("username") String username){
		return this.service.resetPassword(username); 
    }
}
