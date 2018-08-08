package app.db.entities;

public class FotografijaKeyword extends BasicEntity{

	private long fotografjaId;
	private Fotografija fotografija;
	private String keyword;
	private static final String FOTOGRAFIJA_ID = "FotografijaId";
	private static final String KEYWORD= "Keyword";
	
	public FotografijaKeyword() {
		super();
		this.columnsName.add(FOTOGRAFIJA_ID);
		this.columnsName.add(KEYWORD);
	}
	public long getFotografjaId() {
		return fotografjaId;
	}
	public void setFotografjaId(long fotografjaId) {
		this.fotografjaId = fotografjaId;
	}
	public Fotografija getFotografija() {
		return fotografija;
	}
	public void setFotografija(Fotografija fotografija) {
		this.fotografija = fotografija;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	@Override
	public void setValueForColumnName(String columnName, Object value) {
		switch (columnName) {
		case FOTOGRAFIJA_ID:
			this.setFotografjaId((int)value);
			return;
		case KEYWORD:
			this.setKeyword((String)value);
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
			return this.getFotografjaId();
		case KEYWORD:
			return this.getKeyword();
		default:
			break;
		}
		return super.getValueForColumnName(columnName);
	}
}
