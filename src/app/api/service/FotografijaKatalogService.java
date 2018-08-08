package app.api.service;

import app.db.controllers.FotografijaKatalogDbController;
import app.db.entities.FotografijaKatalog;

public class FotografijaKatalogService extends ServiceAbstract<FotografijaKatalog, FotografijaKatalogDbController>{
	public FotografijaKatalogService(FotografijaKatalogDbController dbController) {
		super(dbController);
	}
}
