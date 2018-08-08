package app.db.entities;

public class Komentar extends BasicEntity{

	private long kupacId;
	private Kupac kupac;
	private Prodavac prodavac;
	private long prodavacId;
	private String komentar;
	protected static final String KUPAC_ID = "KupacId";
	protected static final String PRODAVAC_ID = "ProdavacId";
	protected static final String KOMENTAR = "Komentar";
	
	public Komentar() {
		super();
		this.columnsName.add(KUPAC_ID);
		this.columnsName.add(PRODAVAC_ID);
		this.columnsName.add(KOMENTAR);
	}
	public Kupac getKupac() {
		return kupac;
	}
	public void setKupac(Kupac kupac) {
		this.kupac = kupac;
	}
	public String getKomentar() {
		return komentar;
	}
	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}
	public long getKupacId() {
		return kupacId;
	}
	public void setKupacId(long kupacId) {
		this.kupacId = kupacId;
	}
	public Prodavac getProdavac() {
		return prodavac;
	}
	public void setProdavac(Prodavac prodavac) {
		this.prodavac = prodavac;
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
		case KUPAC_ID:
			this.setKupacId((int)value);
			return;
		case PRODAVAC_ID:
			this.setProdavacId((int)value);
			return;
		case KOMENTAR:
			this.setKomentar((String)value);
			return;
		default:
			break;
		}
		super.setValueForColumnName(columnName, value);
	}
	@Override
	public Object getValueForColumnName(String columnName) {
		switch (columnName) {
		case KUPAC_ID:
			return this.getKupacId();
		case PRODAVAC_ID:
			return this.getProdavacId();
		case KOMENTAR:
			return this.getKomentar();
		default:
			break;
		}
		return super.getValueForColumnName(columnName);
	}
}
