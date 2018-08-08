package app.api.service;

import java.util.List;

import javax.ws.rs.core.Response;

import app.api.dto.ResponseDto;
import app.db.controllers.FotografijaKategorijaDbController;
import app.db.entities.FotografijaKategorija;
import app.db.entities.FotografijaKategorijaReport;
import app.utils.Const;

public class FotografijaKategorijaService
		extends ServiceAbstract<FotografijaKategorija, FotografijaKategorijaDbController> {
	public FotografijaKategorijaService(FotografijaKategorijaDbController dbController) {
		super(dbController);
	}

	public Response getCategoriesReport() {
		List<FotografijaKategorijaReport> list = dbController.getCategoriesReport();
		if (list != null) {
			return Response.ok().entity(list).build();
		}
		return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
	}

	public Response update(FotografijaKategorija object) {
		if (dbController.update(object)) {
			return Response.ok().build();
		}
		return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
	}
}
