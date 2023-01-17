package tim24.projekat.uberapp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("driver")
public class ReviewDriver extends Review{
	
	@ManyToOne
	private User driver;

	public ReviewDriver(User driver) {
		super();
		this.driver = driver;
	}

	public ReviewDriver() {
		// TODO Auto-generated constructor stub
	}

	public User getDriver() {
		return driver;
	}

	public void setDriver(User driver) {
		this.driver = driver;
	}
	
	
}
