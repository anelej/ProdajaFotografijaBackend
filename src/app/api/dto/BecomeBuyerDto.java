package app.api.dto;

import java.util.List;

public class BecomeBuyerDto {

	private List<FotografijaSaveDto> photosToSend;
	private int companyId;
	
	public BecomeBuyerDto() {
		// TODO Auto-generated constructor stub
	}
	
	public List<FotografijaSaveDto> getPhotosToSend() {
		return photosToSend;
	}
	public void setPhotosToSend(List<FotografijaSaveDto> photosToSend) {
		this.photosToSend = photosToSend;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	
}
