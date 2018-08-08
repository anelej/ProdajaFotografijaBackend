package app.db.controllers;

import app.db.entities.Kupac;

public class KupacDbController extends AbstractDatabaseController<Kupac>{

	public KupacDbController() {
		super(Kupac.class);
	}
}
