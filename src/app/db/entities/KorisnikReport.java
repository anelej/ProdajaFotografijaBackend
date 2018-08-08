package app.db.entities;

import java.math.BigDecimal;
import java.util.Date;

public class KorisnikReport extends BasicEntity{

	private String fotografijaNaziv;
	private String rezolucija;
	private BigDecimal cena;
	private Date datumKupovine;
	private String datumKupovineString;
	
	protected static final String FOTOGRAFIJA_NAZIV = "f.Naziv";
	protected static final String REZOLUCIJA = "fk.Rezolucija";
	protected static final String CENA = "fk.Cena";
	protected static final String DATUM_KUPOVINE = "i.DatumKupovine";
	
	public KorisnikReport() {
		this.columnsName.add(FOTOGRAFIJA_NAZIV);
		this.columnsName.add(REZOLUCIJA);
		this.columnsName.add(CENA);
		this.columnsName.add(DATUM_KUPOVINE);
	}

	public String getDatumKupovineString() {
		return datumKupovineString;
	}

	public void setDatumKupovineString(String datumKupovineString) {
		this.datumKupovineString = datumKupovineString;
	}

	public String getFotografijaNaziv() {
		return fotografijaNaziv;
	}

	public void setFotografijaNaziv(String fotografijaNaziv) {
		this.fotografijaNaziv = fotografijaNaziv;
	}

	public String getRezolucija() {
		return rezolucija;
	}

	public void setRezolucija(String rezolucija) {
		this.rezolucija = rezolucija;
	}

	public BigDecimal getCena() {
		return cena;
	}

	public void setCena(BigDecimal cena) {
		this.cena = cena;
	}

	public Date getDatumKupovine() {
		return datumKupovine;
	}

	public void setDatumKupovine(Date datumKupovine) {
		this.datumKupovine = datumKupovine;
	}
	
	@Override
	public void setValueForColumnName(String columnName, Object value) {
		switch (columnName) {
		case FOTOGRAFIJA_NAZIV:
			this.setFotografijaNaziv((String) value);
			return;
		case DATUM_KUPOVINE:
			Date date = new Date(((java.sql.Date)value).getTime());
			this.setDatumKupovine(date);
			return;
		case REZOLUCIJA:
			this.setRezolucija((String) value);
			return;
		case CENA:
			this.setCena((BigDecimal) value);
			return;
		default:
			break;
		}
	}

	@Override
	public Object getValueForColumnName(String columnName) {
		switch (columnName) {
		case FOTOGRAFIJA_NAZIV:
			return this.getFotografijaNaziv();
		case DATUM_KUPOVINE:
			if (this.getDatumKupovine() != null) {
				java.sql.Date dateDB = new java.sql.Date(this.getDatumKupovine().getTime());
				return dateDB;
			}
			Date date = new Date();
			java.sql.Date dateDB = new java.sql.Date(date.getTime());
			return dateDB;
		case REZOLUCIJA:
			return this.getRezolucija();
		case CENA:
			return this.getCena();
		default:
			break;
		}
		return null;
	}
}
