package app.api.service;

import app.db.controllers.FotografijaKeywordDbController;
import app.db.entities.FotografijaKeyword;

public class FotografijaKeywordService extends ServiceAbstract<FotografijaKeyword, FotografijaKeywordDbController>{
	public FotografijaKeywordService(FotografijaKeywordDbController dbController) {
		super(dbController);
	}
}
