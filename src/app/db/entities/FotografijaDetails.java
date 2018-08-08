package app.db.entities;

import java.sql.Timestamp;
import java.util.Date;

public class FotografijaDetails extends Fotografija {

	private String prodavacIme;
	private String kategorijaIme;
	private double ocena;
	private int fotografijaOcena;
	private static final String IME = "Username";
	private static final String KATEGORIJA_NAZIV = "kat.Naziv";
	private static final String OCENA = "Ocena";
	private static final String FOTOGRAFIJA_OCENA = "o.ocena";

	public FotografijaDetails() {
		this.columnsName.add(IME);
		this.columnsName.add(KATEGORIJA_NAZIV);
		this.columnsName.add(OCENA);
		this.columnsName.add(FOTOGRAFIJA_OCENA);
	}

	public int getFotografijaOcena() {
		return fotografijaOcena;
	}

	public void setFotografijaOcena(int fotografijaOcena) {
		this.fotografijaOcena = fotografijaOcena;
	}

	public String getProdavacIme() {
		return prodavacIme;
	}

	public void setProdavacIme(String prodavacIme) {
		this.prodavacIme = prodavacIme;
	}

	public String getKategorijaIme() {
		return kategorijaIme;
	}

	public void setKategorijaIme(String kategorijaIme) {
		this.kategorijaIme = kategorijaIme;
	}

	public double getOcena() {
		return ocena;
	}

	public void setOcena(double ocena) {
		this.ocena = ocena;
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {
		switch (columnName) {
		case NAZIV:
			this.setNaziv((String) value);
			return;
		case DATUM_POSTAVLJANJA:
			Date date = new Date(((Timestamp) value).getTime());
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
		case IME:
			this.setProdavacIme((String) value);
		case KATEGORIJA_NAZIV:
			this.setKategorijaIme((String) value);
			return;
		case FOTOGRAFIJA_OCENA:
			this.setFotografijaOcena((int) value);
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
		case IME:
			return this.getProdavacIme();
		case KATEGORIJA_NAZIV:
			return this.getKategorijaIme();
		case FOTOGRAFIJA_OCENA:
			return this.getFotografijaOcena();
		default:
			break;
		}
		return super.getValueForColumnName(columnName);
	}
}
