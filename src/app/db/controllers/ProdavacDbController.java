package app.db.controllers;

import app.db.entities.Prodavac;

public class ProdavacDbController extends AbstractDatabaseController<Prodavac>{

	public ProdavacDbController() {
		super(Prodavac.class);
	}
}
