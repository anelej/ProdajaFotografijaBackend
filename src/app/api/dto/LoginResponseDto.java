package app.api.dto;

import java.util.ArrayList;
import java.util.List;

public class LoginResponseDto {

	private long id;
	private List<Integer> listPurchasePhoto;
	private int uloga;
	private boolean promenaPassworda;
	
	public LoginResponseDto() {
		listPurchasePhoto = new ArrayList<Integer>();
	}
		
	public boolean isPromenaPassworda() {
		return promenaPassworda;
	}


	public void setPromenaPassworda(boolean promenaPassworda) {
		this.promenaPassworda = promenaPassworda;
	}


	public int getUloga() {
		return uloga;
	}

	public void setUloga(int uloga) {
		this.uloga = uloga;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<Integer> getListPurchasePhoto() {
		return listPurchasePhoto;
	}
	public void setListPurchasePhoto(List<Integer> listPurchasePhoto) {
		this.listPurchasePhoto = listPurchasePhoto;
	}
	
}
