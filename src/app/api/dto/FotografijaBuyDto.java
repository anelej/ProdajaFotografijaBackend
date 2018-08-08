package app.api.dto;

import app.db.entities.FotografijaKatalog;

public class FotografijaBuyDto {

	private FotografijaDto photo;
	private FotografijaKatalog resolution;
	private int korisnikId;
	private int card;
	
	public FotografijaBuyDto () {
		
	}

	public int getCard() {
		return card;
	}

	public void setCard(int card) {
		this.card = card;
	}

	public FotografijaDto getPhoto() {
		return photo;
	}

	public void setPhoto(FotografijaDto photo) {
		this.photo = photo;
	}

	public FotografijaKatalog getResolution() {
		return resolution;
	}

	public void setResolution(FotografijaKatalog resolution) {
		this.resolution = resolution;
	}

	public int getKorisnikId() {
		return korisnikId;
	}

	public void setKorisnikId(int korisnikId) {
		this.korisnikId = korisnikId;
	}
	
}
