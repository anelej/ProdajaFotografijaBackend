package app.db.entities;

import java.util.List;

public class Korisnik extends BasicEntity{

	private String username;
	private String password;
	private String email;
	private long drzavaId;
	private Drzava drzava;
	private boolean promenaPasworda;
	private int uloga;
	private boolean aktivan;
	private boolean deaktiviran;
	private List<KreditnaKartica> kreditnaKarticaList;
	private static final String USERNAME = "Username";
	private static final String PASSWORD = "Password";
	private static final String EMAIL = "Email";
	private static final String DRZAVA_ID = "DrzavaId";
	private static final String PROMENA_PASSWORDA = "PromenaPassworda";
	private static final String ULOGA = "Uloga";
	private static final String AKTIVAN = "Aktivan";
	private static final String DEAKTIVIRAN = "Deaktiviran";
	
	public Korisnik () {
		this.columnsName.add(USERNAME);
		this.columnsName.add(PASSWORD);
		this.columnsName.add(EMAIL);
		this.columnsName.add(DRZAVA_ID);
		this.columnsName.add(PROMENA_PASSWORDA);
		this.columnsName.add(ULOGA);
		this.columnsName.add(AKTIVAN);
		this.columnsName.add(DEAKTIVIRAN);
	}
	
	public int getUloga() {
		return uloga;
	}
	public void setUloga(int uloga) {
		this.uloga = uloga;
	}
	public boolean isAktivan() {
		return aktivan;
	}
	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}
	public boolean isDeaktiviran() {
		return deaktiviran;
	}
	public void setDeaktiviran(boolean deaktiviran) {
		this.deaktiviran = deaktiviran;
	}
	public List<KreditnaKartica> getKreditnaKarticaList() {
		return kreditnaKarticaList;
	}
	public void setKreditnaKarticaList(List<KreditnaKartica> kreditnaKarticaList) {
		this.kreditnaKarticaList = kreditnaKarticaList;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Drzava getDrzava() {
		return drzava;
	}
	public void setDrzava(Drzava drzava) {
		this.drzava = drzava;
	}
	public boolean isPromenaPasworda() {
		return promenaPasworda;
	}
	public void setPromenaPasworda(boolean promenaPasworda) {
		this.promenaPasworda = promenaPasworda;
	}
	public long getDrzavaId() {
		return drzavaId;
	}
	public void setDrzavaId(long drzavaId) {
		this.drzavaId = drzavaId;
	}
	
	@Override
	public void setValueForColumnName(String columnName, Object value) {
		switch (columnName) {
		case USERNAME:
			this.setUsername((String)value);
			return;
		case PASSWORD:
			this.setPassword((String)value);
			return;
		case EMAIL:
			this.setEmail((String)value);
			return;	
		case DRZAVA_ID:
			this.setDrzavaId((int)value);
			return;
		case PROMENA_PASSWORDA:
			this.setPromenaPasworda((boolean)value);
			return;
		case ULOGA:
			this.setUloga((int)value);
			return;
		case AKTIVAN:
			this.setAktivan((boolean)value);
			return;
		case DEAKTIVIRAN:
			this.setDeaktiviran((boolean)value);
			return;
		default:
			break;
		}
		super.setValueForColumnName(columnName, value);
	}
	@Override
	public Object getValueForColumnName(String columnName) {
		switch (columnName) {
		case USERNAME:
			return this.getUsername();
		case PASSWORD:
			return this.getPassword();
		case EMAIL:
			return this.getEmail();
		case DRZAVA_ID:
			return this.getDrzavaId();
		case PROMENA_PASSWORDA:
			return this.isPromenaPasworda();
		case ULOGA:
			return this.getUloga();
		case AKTIVAN:
			return this.isAktivan();
		case DEAKTIVIRAN:
			return this.isDeaktiviran();
		default:
			break;
		}
		return super.getValueForColumnName(columnName);
	}
}
