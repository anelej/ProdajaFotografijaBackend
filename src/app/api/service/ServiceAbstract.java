package app.api.service;

import java.util.List;
import javax.ws.rs.core.Response;

import app.api.dto.ResponseDto;
import app.db.controllers.AbstractDatabaseController;
import app.db.entities.BasicEntity;
import app.utils.Const;

public abstract class ServiceAbstract<T extends BasicEntity, DBC extends AbstractDatabaseController<T>> {
	public ServiceAbstract(DBC dbController) {
		this.dbController = dbController;
	}

	public Response add(T object) {
		if (this.dbController.add(object) != -1) {
			return Response.ok().build();
		} else {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
	}

	public Response removeById(long id) {
		if (this.dbController.removeById(id)) {
			return Response.ok().build();
		} else {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
	}

	public Response update(T object) {
		if (this.dbController.update(object)) {
			return Response.ok().build();
		} else {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
	}

	public Response getAll() {
		List<T> result = this.dbController.getAll();
		if (result != null) {
			return Response.ok(result).build();
		} else {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
	}

	public Response getById(long id) {
		T result = this.dbController.getById(id);
		if (result != null) {
			return Response.ok(result).build();
		} else {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
	}

	protected DBC dbController;
}
