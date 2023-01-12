package tim24.projekat.uberapp.DTO;

public class WorkingHourPutDTO {
	
	private String end;

	public WorkingHourPutDTO(String end) {
		super();
		this.end = end;
	}

	public WorkingHourPutDTO() {
		super();
	}

	public String getend() {
		return end;
	}

	public void setend(String end) {
		this.end = end;
	}

}
