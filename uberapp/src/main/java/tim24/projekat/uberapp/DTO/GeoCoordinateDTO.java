package tim24.projekat.uberapp.DTO;

import tim24.projekat.uberapp.model.Location;

public class GeoCoordinateDTO {
	
	private String address;
	private double latitude;
	private double longitude;

	public GeoCoordinateDTO()
	{
		super();
	}

	public GeoCoordinateDTO(String address, double latitude, double longitude) {
		super();
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public GeoCoordinateDTO(Location loc) {
		super();
		this.address = loc.getAddress();
		this.latitude = loc.getGeoWidth();
		this.longitude = loc.getGeoHeight();
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	@Override
	public String toString() {
		return "GeoCoordinate [address=" + address + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	
	

}
