package app.db.entities;

import java.math.BigDecimal;

public class FotografijaKategorijaReport extends BasicEntity{

	private String naziv;
	private BigDecimal brojProdatihSlika;
	private String najcescaRezolucija;
	private BigDecimal prosecnaCena;
	
	private static final String NAZIV = "fk.Naziv";
	private static final String BROJ_PRODATIH_SLIKA = "SUM(i.Id)";
	private static final String NAJCESCA_REZOLUCIJA = "t.Rezolucija";
	private static final String PROSECNA_CENA = "AVG(kat.Cena)";
	
	public FotografijaKategorijaReport() {
		this.columnsName.add(NAZIV);
		this.columnsName.add(BROJ_PRODATIH_SLIKA);
		this.columnsName.add(NAJCESCA_REZOLUCIJA);
		this.columnsName.add(PROSECNA_CENA);
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public BigDecimal getBrojProdatihSlika() {
		return brojProdatihSlika;
	}

	public void setBrojProdatihSlika(BigDecimal brojProdatihSlika) {
		this.brojProdatihSlika = brojProdatihSlika;
	}

	public String getNajcescaRezolucija() {
		return najcescaRezolucija;
	}

	public void setNajcescaRezolucija(String najcescaRezolucija) {
		this.najcescaRezolucija = najcescaRezolucija;
	}

	public BigDecimal getProsecnaCena() {
		return prosecnaCena;
	}

	public void setProsecnaCena(BigDecimal prosecnaCena) {
		this.prosecnaCena = prosecnaCena;
	}
	
	@Override
	public void setValueForColumnName(String columnName, Object value) {
		switch (columnName) {
		case NAZIV:
			this.setNaziv((String)value);
			return;
		case BROJ_PRODATIH_SLIKA:
			this.setBrojProdatihSlika((BigDecimal)value);
			return;
		case NAJCESCA_REZOLUCIJA:
			this.setNajcescaRezolucija((String) value);
			return;
		case PROSECNA_CENA:
			this.setProsecnaCena((BigDecimal) value);
			return;
		default:
			break;
		}
	}
	@Override
	public Object getValueForColumnName(String columnName) {
		switch (columnName) {
		case NAZIV:
			return this.getNaziv();
		case BROJ_PRODATIH_SLIKA:
			return this.getBrojProdatihSlika();
		case NAJCESCA_REZOLUCIJA:
			return this.getNajcescaRezolucija();
		case PROSECNA_CENA:
			return this.getProsecnaCena();
		default:
			break;
		}
		return null;
	}
}
