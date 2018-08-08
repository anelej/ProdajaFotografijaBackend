package app.api.dto;

import java.util.Date;
import java.util.List;

import app.db.entities.FotografijaKatalog;

public class FotografijaSaveDto {

	private String naziv;
	private Date datumPostavljanja;
	private int ocena;
	private String mesto;
	private int fotografijaKategorijaId;
	private int prodavacId;
	private String opis;
	private boolean odobrena;
	private String putanja;
	private String src;
	private List<FotografijaKatalog> listaRezolucija;
	private String keywords;
	
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public List<FotografijaKatalog> getListaRezolucija() {
		return listaRezolucija;
	}

	public void setListaRezolucija(List<FotografijaKatalog> listaRezolucija) {
		this.listaRezolucija = listaRezolucija;
	}

	public FotografijaSaveDto() {
	
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Date getDatumPostavljanja() {
		return datumPostavljanja;
	}

	public void setDatumPostavljanja(Date datumPostavljanja) {
		this.datumPostavljanja = datumPostavljanja;
	}

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public int getFotografijaKategorijaId() {
		return fotografijaKategorijaId;
	}

	public void setFotografijaKategorijaId(int fotografijaKategorijaId) {
		this.fotografijaKategorijaId = fotografijaKategorijaId;
	}

	public int getProdavacId() {
		return prodavacId;
	}

	public void setProdavacId(int prodavacId) {
		this.prodavacId = prodavacId;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public boolean isOdobrena() {
		return odobrena;
	}

	public void setOdobrena(boolean odobrena) {
		this.odobrena = odobrena;
	}

	public String getPutanja() {
		return putanja;
	}

	public void setPutanja(String putanja) {
		this.putanja = putanja;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
	
}
