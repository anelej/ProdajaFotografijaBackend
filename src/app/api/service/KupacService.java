package app.api.service;

import app.db.controllers.KupacDbController;
import app.db.entities.Kupac;

public class KupacService extends ServiceAbstract<Kupac, KupacDbController>{
	public KupacService(KupacDbController dbController) {
		super(dbController);
	}
}