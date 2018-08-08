
package app.api.dto;

public class FotografijaSearchDto {

	private int id;
	private String naziv;
	private int fotografijaKategorijaId;
	private int prodavacId;
	private String keyword;
	private int page;
	private int pageSize;
	private String sortOrder;
	private String sortDirection;
	private boolean odobrena;
	private boolean kupljeneFotografije;
	private int kupacId;
	
	public int getKupacId() {
		return kupacId;
	}
	public void setKupacId(int kupacId) {
		this.kupacId = kupacId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isOdobrena() {
		return odobrena;
	}
	public void setOdobrena(boolean odobrena) {
		this.odobrena = odobrena;
	}
	public String getSortDirection() {
		return sortDirection;
	}
	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getFotografijaKategorijaId() {
		return fotografijaKategorijaId;
	}
	public void setFotografijaKategorijaId(int fotografijaKategorijaId) {
		this.fotografijaKategorijaId = fotografijaKategorijaId;
	}
	public FotografijaSearchDto() {
		
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public int getProdavacId() {
		return prodavacId;
	}
	public void setProdavacId(int prodavacId) {
		this.prodavacId = prodavacId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public boolean isKupljeneFotografije() {
		return kupljeneFotografije;
	}
	public void setKupljeneFotografije(boolean kupljeneFotografije) {
		this.kupljeneFotografije = kupljeneFotografije;
	}
	
}
