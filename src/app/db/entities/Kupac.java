package app.db.entities;

import java.util.List;

public class Kupac extends BasicEntity{

	private long KorisnikId;
	private Korisnik korisnik;
	private List<IstorijaKupovine> istorijaKupovine;
	private static final String KORISNIK_ID = "KorisnikId";
	
	public Kupac() {
		super();
		this.columnsName.add(KORISNIK_ID);
	}
	public List<IstorijaKupovine> getIstorijaKupovine() {
		return istorijaKupovine;
	}
	public void setIstorijaKupovine(List<IstorijaKupovine> istorijaKupovine) {
		this.istorijaKupovine = istorijaKupovine;
	}
	public Korisnik getKorisnik() {
		return korisnik;
	}
	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	public long getKorisnikId() {
		return KorisnikId;
	}
	public void setKorisnikId(long korisnikId) {
		KorisnikId = korisnikId;
	}
	
	@Override
	public void setValueForColumnName(String columnName, Object value) {
		switch (columnName) {
		case KORISNIK_ID:
			this.setKorisnikId((int)value);
			return;
		default:
			break;
		}
		super.setValueForColumnName(columnName, value);
	}
	@Override
	public Object getValueForColumnName(String columnName) {
		switch (columnName) {
		case KORISNIK_ID:
			return this.getKorisnikId();
		default:
			break;
		}
		return super.getValueForColumnName(columnName);
	}
	
}
