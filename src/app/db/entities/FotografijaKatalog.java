package app.db.entities;

import java.math.BigDecimal;
import java.util.List;

public class FotografijaKatalog extends BasicEntity{

	private long fotografijaId;
	private Fotografija fotografija;
	private BigDecimal cena;
	private String rezolucija;
	private List<IstorijaKupovine> istorijaKupovineList;
	private static final String FOTOGRAFIJA_ID = "FotografijaId";
	private static final String CENA = "Cena";
	private static final String REZOLUCIJA = "Rezolucija";
	
	public FotografijaKatalog() {
		super();
		this.columnsName.add(FOTOGRAFIJA_ID);
		this.columnsName.add(CENA);
		this.columnsName.add(REZOLUCIJA);
	}
	public long getFotografijaId() {
		return fotografijaId;
	}
	public void setFotografijaId(long fotografijaId) {
		this.fotografijaId = fotografijaId;
	}
	public List<IstorijaKupovine> getIstorijaKupovineList() {
		return istorijaKupovineList;
	}
	public void setIstorijaKupovineList(List<IstorijaKupovine> istorijaKupovineList) {
		this.istorijaKupovineList = istorijaKupovineList;
	}
	public Fotografija getFotografija() {
		return fotografija;
	}
	public void setFotografija(Fotografija fotografija) {
		this.fotografija = fotografija;
	}
	public BigDecimal getCena() {
		return cena;
	}
	public void setCena(BigDecimal cena) {
		this.cena = cena;
	}
	public String getRezolucija() {
		return rezolucija;
	}
	public void setRezolucija(String rezolucija) {
		this.rezolucija = rezolucija;
	}
	
	@Override
	public void setValueForColumnName(String columnName, Object value) {
		switch (columnName) {
		case FOTOGRAFIJA_ID:
			this.setFotografijaId((int)value);
			return;
		case CENA:
			this.setCena((BigDecimal)value);
			return;
		case REZOLUCIJA:
			this.setRezolucija((String) value);
			return;
		default:
			break;
		}
		super.setValueForColumnName(columnName, value);
	}
	@Override
	public Object getValueForColumnName(String columnName) {
		switch (columnName) {
		case FOTOGRAFIJA_ID:
			return this.getFotografijaId();
		case CENA:
			return this.getCena();
		case REZOLUCIJA:
			return this.getRezolucija();
		default:
			break;
		}
		return super.getValueForColumnName(columnName);
	}
}
