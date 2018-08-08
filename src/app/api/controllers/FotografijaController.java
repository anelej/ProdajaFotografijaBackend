package app.api.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import app.api.dto.FotografijaBuyDto;
import app.api.dto.FotografijaOdobriObrisi;
import app.api.dto.FotografijaRateDto;
import app.api.dto.FotografijaSaveDto;
import app.api.dto.FotografijaSearchDto;
import app.api.dto.ResponseDto;
import app.api.service.FotografijaService;
import app.db.controllers.FotografijaDbController;
import app.db.entities.Fotografija;

@Path("/fotografija")
public class FotografijaController extends AbstractApiController<Fotografija, FotografijaDbController> {

	private FotografijaService service;

	public FotografijaController() {
		this.service = new FotografijaService(new FotografijaDbController());
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/searchPhoto")
	public Response searchPhoto(FotografijaSearchDto object) {
		object.setOdobrena(true);
		return this.service.searchPhoto(object);
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/searchUnapprovedPhoto")
	public Response searchUnapprovedPhoto() {
		FotografijaSearchDto object = new FotografijaSearchDto();
		object.setOdobrena(false);
		return this.service.searchPhoto(object);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/oceni")
	public Response ratePhoto(FotografijaRateDto object) {
		return this.service.ratePhoto(object);
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/getAll")
	public Response getAll() {
		return this.service.getAll();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/add")
	public Response add(FotografijaSaveDto object) {
		return this.service.addPhotoWithValidation(object);
	}

	@GET
	@Path("/getById")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response getById(@QueryParam("id") int id, @QueryParam("korisnikId") int korisnikId) {
		return this.service.getFotografijaById(id, korisnikId);
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/delete")
	public Response remove(@QueryParam("id") int id) {
		return this.service.removeById(id);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/update")
	public Response update(Fotografija object) {
		return this.service.update(object);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/odobri")
	public Response approve(FotografijaOdobriObrisi object) {
		return this.service.approvePhoto(object.getFotografijaId());
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/sendForApproval")
	public Response sendForApproval(List<FotografijaSaveDto> object) {
		for (int i = 0; i< object.size(); i++) {
			object.get(i).setNaziv("probnaFotografija" + object.get(i).getProdavacId() + i);
			Response response = this.service.add(object.get(i));
			ResponseDto responseDto = (ResponseDto) response.getEntity();
			if (responseDto == null) {
				continue;
			}
			if (responseDto.getError() != null && !responseDto.getError().isEmpty()) {
				return response;
			}
		}
		return Response.ok().build();
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/getPhotosFromBuyer")
	public Response getPhotosFromBuyer(@QueryParam("id") int id) {
		return this.service.getPhotosFromBuyer(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/buy")
	public Response buy(List<FotografijaBuyDto> object) {
		return this.service.buy(object);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/sendPhotoOnMail")
	public Response sendPhotoOnMail(FotografijaRateDto object) {
		return this.service.sendPhotoOnMail(object);
	}
	
}
