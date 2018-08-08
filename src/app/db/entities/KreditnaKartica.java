package app.db.entities;

public class KreditnaKartica extends BasicEntity{

	private long KorisnikId;
	private Korisnik korisnik;
	private int brojKartice;
	private static final String KORISNIK_ID = "KorisnikId";
	private static final String BROJ_KARTICE = "BrojKartice";
	
	public KreditnaKartica() {
		super();
		this.columnsName.add(KORISNIK_ID);
		this.columnsName.add(BROJ_KARTICE);
	}
	public Korisnik getKorisnik() {
		return korisnik;
	}
	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	public int getBrojKartice() {
		return brojKartice;
	}
	public void setBrojKartice(int brojKartice) {
		this.brojKartice = brojKartice;
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
		case BROJ_KARTICE:
			this.setBrojKartice((int)value);
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
		case BROJ_KARTICE:
			return this.getBrojKartice();
		default:
			break;
		}
		return super.getValueForColumnName(columnName);
	}
}
