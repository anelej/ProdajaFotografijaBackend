package app.db.controllers;

import app.db.entities.Drzava;

public class DrzavaDbController extends AbstractDatabaseController<Drzava>{

	public DrzavaDbController() {
		super(Drzava.class);
	}
}
