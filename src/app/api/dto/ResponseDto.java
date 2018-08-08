package app.api.dto;

public class ResponseDto {
	private String error;
	private Object result;
	public ResponseDto(Object result, String error) {
		this.result = result;
		this.error = error;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
}
