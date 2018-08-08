package app.api.service;

import app.db.controllers.DrzavaDbController;
import app.db.entities.Drzava;

public class DrzavaService extends ServiceAbstract<Drzava, DrzavaDbController>{
	public DrzavaService(DrzavaDbController dbController) {
		super(dbController);
	}
}