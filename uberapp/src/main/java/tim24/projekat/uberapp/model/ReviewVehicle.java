package tim24.projekat.uberapp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
@DiscriminatorValue("vehicle")
public class ReviewVehicle extends Review{
	
	@ManyToOne
	private Vehicle vehicle;

	public Vehicle getVehicle() {
		return vehicle;
	}
	
	

	public ReviewVehicle() {
		super();
		// TODO Auto-generated constructor stub
	}



	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public ReviewVehicle(Vehicle vehicle) {
		super();
		this.vehicle = vehicle;
	}
	
	
}
