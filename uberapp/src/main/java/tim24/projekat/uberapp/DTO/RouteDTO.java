package tim24.projekat.uberapp.DTO;

import tim24.projekat.uberapp.model.Location;
import tim24.projekat.uberapp.model.Route;

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
	public RouteDTO(Location dep, Location dest) {
		super();
		this.departure = new GeoCoordinateDTO(dep);
		this.destination = new GeoCoordinateDTO(dest);
	}

	public RouteDTO(Route route) {
		super();
		this.departure = new GeoCoordinateDTO(route.getStartLocation());
		this.destination = new GeoCoordinateDTO(route.getEndLocation());
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
