package app.api.service;

import java.util.List;

import javax.ws.rs.core.Response;

import app.api.dto.ResponseDto;
import app.db.controllers.KomentarDbController;
import app.db.entities.Komentar;
import app.db.entities.KomentarDetails;
import app.utils.Const;

public class KomentarService extends ServiceAbstract<Komentar, KomentarDbController>{
	public KomentarService(KomentarDbController dbController) {
		super(dbController);
	}
	
	public Response getCommentsForSeller(int sellerId) {
		List<KomentarDetails> list = dbController.getCommentsForSeller(sellerId);
		if(list != null) {
			return Response.ok(list).build();
		} else {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
	}
}