package tim24.projekat.uberapp.DTO;

public class MapDetailDTO {
	
	private double latitude;
	private double longitude;
	private String status;
	
	
	public MapDetailDTO(double latitude, double longitude, String status) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.status = status;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	

}
