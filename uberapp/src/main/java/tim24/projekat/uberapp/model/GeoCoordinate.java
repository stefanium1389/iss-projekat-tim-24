package tim24.projekat.uberapp.model;

public class GeoCoordinate {
	
	private String address;
	private int latitude;
	private int longitude;
	public GeoCoordinate(String address, int latitude, int longitude) {
		super();
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getLatitude() {
		return latitude;
	}
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	public int getLongitude() {
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
