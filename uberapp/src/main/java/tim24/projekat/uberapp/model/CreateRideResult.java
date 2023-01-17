package tim24.projekat.uberapp.model;

public class CreateRideResult {
	
	private User driver;
	private int minutes;
	
	public CreateRideResult(User driver, int minutes) {
		super();
		this.driver = driver;
		this.minutes = minutes;
	}

	public User getDriver() {
		return driver;
	}

	public void setDriver(User driver) {
		this.driver = driver;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
	
	
}
