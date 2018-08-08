package app.api.service;

import app.api.dto.ResponseDto;
import app.db.controllers.KreditnaKarticaDbController;
import app.db.entities.KreditnaKartica;
import app.utils.Const;

import java.util.List;

import javax.ws.rs.core.Response;

public class KreditnaKarticaService extends ServiceAbstract<KreditnaKartica, KreditnaKarticaDbController>{
	public KreditnaKarticaService(KreditnaKarticaDbController dbController) {
		super(dbController);
	}
	
	public Response getForUser(int id) {
		List<KreditnaKartica> result = dbController.getForUser(id);
		if (result != null) {
			return Response.ok().entity(new ResponseDto(result, null)).build();
		}
		return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
	}
	
	public Response update(KreditnaKartica object) {
		if (dbController.update(object)) {
			return Response.ok().build();
		}
		return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
	}
}