package app.db.entities;

import java.util.Date;

public class IstorijaKupovine extends BasicEntity{

	private long KorisnikId;
	private long fotografijaKatalogId;
	private FotografijaKatalog fotografijaKatalog;
	private Date datumKupovine;
	private static final String KORISNIK_ID = "KorisnikId";
	private static final String FOTOGRAFIJA_KATALOG_ID = "FotografijaKatalogId";
	private static final String DATUM_KUPOVINE = "DatumKupovine";
	
	public long getKorisnikId() {
		return KorisnikId;
	}
	public void setKorisnikId(long korisnikId) {
		KorisnikId = korisnikId;
	}
	public IstorijaKupovine() {
		super();
		this.columnsName.add(KORISNIK_ID);
		this.columnsName.add(FOTOGRAFIJA_KATALOG_ID);
		this.columnsName.add(DATUM_KUPOVINE);
	}
	public FotografijaKatalog getFotografijaKatalog() {
		return fotografijaKatalog;
	}
	public void setFotografijaKatalog(FotografijaKatalog fotografijaKatalog) {
		this.fotografijaKatalog = fotografijaKatalog;
	}
	public Date getDatumKupovine() {
		return datumKupovine;
	}
	public void setDatumKupovine(Date datumKupovine) {
		this.datumKupovine = datumKupovine;
	}
	public long getFotografijaKatalogId() {
		return fotografijaKatalogId;
	}
	public void setFotografijaKatalogId(long fotografijaKatalogId) {
		this.fotografijaKatalogId = fotografijaKatalogId;
	}
	
	@Override
	public void setValueForColumnName(String columnName, Object value) {
		switch (columnName) {
		case KORISNIK_ID:
			this.setKorisnikId((long)value);
			return;
		case FOTOGRAFIJA_KATALOG_ID:
			this.setFotografijaKatalogId((long)value);
			return;
		case DATUM_KUPOVINE:
			java.sql.Date sqlDate = (java.sql.Date)value;
			Date date =  new Date(sqlDate.getTime());
			this.setDatumKupovine(date);
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
		case FOTOGRAFIJA_KATALOG_ID:
			return this.getFotografijaKatalogId();
		case DATUM_KUPOVINE:
			return this.getDatumKupovine();
		default:
			break;
		}
		return super.getValueForColumnName(columnName);
	}
}
