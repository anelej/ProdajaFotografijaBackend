package app.db.entities;

import java.util.List;

public class Prodavac extends BasicEntity{

	private long KorisnikId;
	private Korisnik korisnik;
	private List<Fotografija> fotografijaList;
	private static final String KORISNIK_ID = "KorisnikId";

	public Prodavac() {
		super();
		this.columnsName.add(KORISNIK_ID);
	}
	public List<Fotografija> getFotografijaList() {
		return fotografijaList;
	}
	public void setFotografijaList(List<Fotografija> fotografijaList) {
		this.fotografijaList = fotografijaList;
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
			this.setKorisnikId((long)value);
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
