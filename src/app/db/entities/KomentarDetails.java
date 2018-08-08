package app.db.entities;

public class KomentarDetails extends Komentar{

	private String kupacUsername;
	private static final String KUPAC_USERNAME = "ko.Username";
	public KomentarDetails() {
		super();
		this.columnsName.add(KUPAC_USERNAME);
	}

	public String getKupacUsername() {
		return kupacUsername;
	}

	public void setKupacUsername(String kupacUsername) {
		this.kupacUsername = kupacUsername;
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
		case KUPAC_USERNAME:
			this.setKupacUsername((String)value);
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
		case KUPAC_USERNAME:
			return this.getKupacUsername();
		default:
			break;
		}
		return super.getValueForColumnName(columnName);
	}
}
