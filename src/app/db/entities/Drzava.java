package app.db.entities;

public class Drzava extends BasicEntity{

	private String naziv;
	private static final String NAZIV = "Naziv";

	public Drzava () {
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
		if( NAZIV.equals(columnName) ){
			this.setNaziv((String) value);
			return;
		}
		super.setValueForColumnName(columnName, value);
	}
	@Override
	public Object getValueForColumnName(String columnName) {
		if( NAZIV.equals(columnName) ){
			return this.getNaziv();
		}
		return super.getValueForColumnName(columnName);
	}
}
