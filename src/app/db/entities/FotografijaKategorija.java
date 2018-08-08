package app.db.entities;

public class FotografijaKategorija extends BasicEntity{

	private String naziv;
	private static final String NAZIV = "Naziv";
	
	public FotografijaKategorija() {
		super();
		this.columnsName.add(NAZIV);
	}
	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	@Override
	public void setValueForColumnName(String columnName, Object value) {
		switch (columnName) {
		case NAZIV:
			this.setNaziv((String)value);
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
		default:
			break;
		}
		return super.getValueForColumnName(columnName);
	}
}
