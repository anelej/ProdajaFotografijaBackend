package app.api.dto;

import java.util.Date;
import java.util.List;

import app.db.entities.FotografijaDetails;
import app.db.entities.FotografijaKatalog;

public class FotografijaDto {
	
	private String naziv;
	private Date datumPostavljanja;
	private double ocena;
	private String mesto;
	private long fotografijaKategorijaId;
	private long prodavacId;
	private String opis;
	private boolean odobrena;
	private String src;
	private String keywords;
	private String prodavacIme;
	private String kategorijaIme;
	private int fotografijaOcena;
	private List<FotografijaKatalog> listaRezolucija;
	private long id;
	
	public FotografijaDto(FotografijaDetails fotografija) {
		naziv = fotografija.getNaziv();
		datumPostavljanja = fotografija.getDatumPostavljanja();
		mesto = fotografija.getMesto();
		fotografijaKategorijaId = fotografija.getFotografijaKategorijaId();
		prodavacId = fotografija.getProdavacId();
		opis = fotografija.getOpis();
		odobrena = fotografija.isOdobrena();
		prodavacIme = fotografija.getProdavacIme();
		kategorijaIme = fotografija.getKategorijaIme();
		id = fotografija.getId();
		ocena = fotografija.getOcena();
		listaRezolucija = fotografija.getFotografijaKatalogList();
		fotografijaOcena = fotografija.getFotografijaOcena();
	}
	
	public int getFotografijaOcena() {
		return fotografijaOcena;
	}

	public void setFotografijaOcena(int fotografijaOcena) {
		this.fotografijaOcena = fotografijaOcena;
	}

	public FotografijaDto() {
		
	}

	public List<FotografijaKatalog> getListaRezolucija() {
		return listaRezolucija;
	}

	public void setListaRezolucija(List<FotografijaKatalog> listaRezolucija) {
		this.listaRezolucija = listaRezolucija;
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getprodavacIme() {
		return prodavacIme;
	}

	public void setprodavacIme(String ime) {
		this.prodavacIme = ime;
	}

	public String getKategorijaIme() {
		return kategorijaIme;
	}

	public void setKategorijaIme(String kategorijaIme) {
		this.kategorijaIme = kategorijaIme;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
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
	public double getOcena() {
		return ocena;
	}
	public void setOcena(double ocena) {
		this.ocena = ocena;
	}
	public String getMesto() {
		return mesto;
	}
	public void setMesto(String mesto) {
		this.mesto = mesto;
	}
	public long getFotografijaKategorijaId() {
		return fotografijaKategorijaId;
	}
	public void setFotografijaKategorijaId(long fotografijaKategorijaId) {
		this.fotografijaKategorijaId = fotografijaKategorijaId;
	}
	public long getProdavacId() {
		return prodavacId;
	}
	public void setProdavacId(long prodavacId) {
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
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	
}
