package app.api.dto;

public class FotografijaRateDto {
	private int fotografijaId;
	private int ocena;
	private int korisnikId;

	public int getKorisnikId() {
		return korisnikId;
	}

	public void setKorisnikId(int korisnikId) {
		this.korisnikId = korisnikId;
	}

	public int getFotografijaId() {
		return fotografijaId;
	}

	public void setFotografijaId(int fotografijaId) {
		this.fotografijaId = fotografijaId;
	}

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public FotografijaRateDto() {
		
	}
}
