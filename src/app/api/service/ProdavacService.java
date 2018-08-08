package app.api.service;

import app.db.controllers.ProdavacDbController;
import app.db.entities.Prodavac;

public class ProdavacService extends ServiceAbstract<Prodavac, ProdavacDbController>{
	public ProdavacService(ProdavacDbController dbController) {
		super(dbController);
	}
}
