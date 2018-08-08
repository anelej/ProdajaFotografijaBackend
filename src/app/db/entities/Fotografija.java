package app.db.entities;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import app.api.dto.FotografijaSaveDto;

public class Fotografija extends BasicEntity {

	private String naziv;
	private Date datumPostavljanja;
	private String mesto;
	private long fotografijaKategorijaId;
	private FotografijaKategorija fotografijaKategorija;
	private long prodavacId;
	private Prodavac prodavac;
	private String opis;
	private boolean odobrena;
	private String putanja;
	private List<FotografijaKeyword> fotografijaKeywordList;
	private List<FotografijaKatalog> fotografijaKatalogList;
	
	
	
	protected static final String NAZIV = "Naziv";
	protected static final String DATUM_POSTAVLJANJA = "DatumPostavljanja";
	protected static final String MESTO = "Mesto";
	protected static final String FOTOGRAFIJA_KATEGORIJA_ID = "FotografijaKategorijaId";
	protected static final String PRODAVAC_ID = "ProdavacId";
	protected static final String OPIS = "Opis";
	protected static final String ODOBRENA = "Odobrena";
	protected static final String PUTANJA = "Putanja";

	public Fotografija() {
		super();
		this.columnsName.add(NAZIV);
		this.columnsName.add(DATUM_POSTAVLJANJA);
		this.columnsName.add(MESTO);
		this.columnsName.add(FOTOGRAFIJA_KATEGORIJA_ID);
		this.columnsName.add(PRODAVAC_ID);
		this.columnsName.add(OPIS);
		this.columnsName.add(ODOBRENA);
		this.columnsName.add(PUTANJA);
		
	}

	public List<FotografijaKatalog> getFotografijaKatalogList() {
		return fotografijaKatalogList;
	}

	public void setFotografijaKatalogList(List<FotografijaKatalog> fotografijaKatalogList) {
		this.fotografijaKatalogList = fotografijaKatalogList;
	}

	public String getPutanja() {
		return putanja;
	}

	public void setPutanja(String putanja) {
		this.putanja = putanja;
	}

	public List<FotografijaKeyword> getFotografijaKeywordList() {
		return fotografijaKeywordList;
	}

	public void setFotografijaKeywordList(List<FotografijaKeyword> fotografijaKeywordList) {
		this.fotografijaKeywordList = fotografijaKeywordList;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Date getDatumPostavljanja() {
		return datumPostavljanja;
	}

	public void setDatumPostavljanja(Date datumPostavljanja) {
		this.datumPostavljanja = datumPostavljanja;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public FotografijaKategorija getFotografijaKategorija() {
		return fotografijaKategorija;
	}

	public void setFotografijaKategorija(FotografijaKategorija kategorija) {
		this.fotografijaKategorija = kategorija;
	}

	public Prodavac getProdavac() {
		return prodavac;
	}

	public void setProdavac(Prodavac prodavac) {
		this.prodavac = prodavac;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public boolean isOdobrena() {
		return odobrena;
	}

	public void setOdobrena(boolean odobrena) {
		this.odobrena = odobrena;
	}

	public long getFotografijaKategorijaId() {
		return fotografijaKategorijaId;
	}

	public void setFotografijaKategorijaId(long fotografijaKategorijaId) {
		this.fotografijaKategorijaId = fotografijaKategorijaId;
	}

	public long getProdavacId() {
		return prodavacId;
	}

	public void setProdavacId(long prodavacId) {
		this.prodavacId = prodavacId;
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {
		switch (columnName) {
		case NAZIV:
			this.setNaziv((String) value);
			return;
		case DATUM_POSTAVLJANJA:
			Date date = new Date(((Timestamp)value).getTime());
			this.setDatumPostavljanja(date);
			return;
		case MESTO:
			this.setMesto((String) value);
			return;
		case FOTOGRAFIJA_KATEGORIJA_ID:
			this.setFotografijaKategorijaId((int) value);
			return;
		case PRODAVAC_ID:
			this.setProdavacId((int) value);
			return;
		case OPIS:
			this.setOpis((String) value);
			return;
		case PUTANJA:
			this.setPutanja((String) value);
			return;
		case ODOBRENA:
			this.setOdobrena((boolean) value);
			return;
		default:
			break;
		}
		super.setValueForColumnName(columnName, value);
	}

	@Override
	public Object getValueForColumnName(String columnName) {
		switch (columnName) {
		case NAZIV:
			return this.getNaziv();
		case DATUM_POSTAVLJANJA:
			if (this.getDatumPostavljanja() != null) {
				java.sql.Date dateDB = new java.sql.Date(this.getDatumPostavljanja().getTime());
				return dateDB;
			}
			Date date = new Date();
			java.sql.Date dateDB = new java.sql.Date(date.getTime());
			return dateDB;
		case MESTO:
			return this.getMesto();
		case FOTOGRAFIJA_KATEGORIJA_ID:
			return this.getFotografijaKategorijaId();
		case PRODAVAC_ID:
			return this.getProdavacId();
		case OPIS:
			return this.getOpis();
		case PUTANJA:
			return this.getPutanja();
		case ODOBRENA:
			return this.isOdobrena();
		default:
			break;
		}
		return super.getValueForColumnName(columnName);
	}

	public Fotografija(FotografijaSaveDto foto) {
		this();
		setDatumPostavljanja(foto.getDatumPostavljanja());
		setFotografijaKategorijaId(foto.getFotografijaKategorijaId());
		setMesto(foto.getMesto());
		setNaziv(foto.getNaziv());
		setOdobrena(foto.isOdobrena());
		setOpis(foto.getOpis());
		setProdavacId(foto.getProdavacId());
		setPutanja(foto.getPutanja());
	}
}
