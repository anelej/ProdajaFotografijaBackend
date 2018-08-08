package app.api.service;

import app.db.controllers.IstorijaKupovineDbController;
import app.db.entities.IstorijaKupovine;

public class IstorijaKupovineService extends ServiceAbstract<IstorijaKupovine, IstorijaKupovineDbController>{
	public IstorijaKupovineService(IstorijaKupovineDbController dbController) {
		super(dbController);
	}
}
