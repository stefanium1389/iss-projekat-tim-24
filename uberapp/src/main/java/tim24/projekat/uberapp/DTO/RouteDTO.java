package tim24.projekat.uberapp.DTO;

public class RouteDTO {

	private GeoCoordinateDTO departure;
	private GeoCoordinateDTO destination;
	
	public RouteDTO() {
		super();
	}
	
	public RouteDTO(GeoCoordinateDTO departure, GeoCoordinateDTO destination) {
		super();
		this.departure = departure;
		this.destination = destination;
	}
	public GeoCoordinateDTO getDeparture() {
		return departure;
	}
	public void setDeparture(GeoCoordinateDTO departure) {
		this.departure = departure;
	}
	public GeoCoordinateDTO getDestination() {
		return destination;
	}
	public void setDestination(GeoCoordinateDTO destination) {
		this.destination = destination;
	}
	
	
	
}
