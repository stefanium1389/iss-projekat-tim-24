package tim24.projekat.uberapp.model;

import jakarta.persistence.*;

@Table(name = "routes")
@Entity
public class Route
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;
    private double lenght; //in km
    private int estimatedTimeInMinutes;
    @OneToOne
    private Location startLocation;
    @OneToOne
    private Location endLocation;

    public Route()
    {
        super();
    }

	public Route(Long id, double lenght, int estimatedTimeInMinutes, Location startLocation, Location endLocation) {
		super();
		this.id = id;
		this.lenght = lenght;
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
	}
	

	public Route(double lenght, int estimatedTimeInMinutes, Location startLocation, Location endLocation) {
		super();
		this.lenght = lenght;
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getLenght() {
		return lenght;
	}
	
	public double getLenghtInKm() {
		return lenght/1000;
	}

	public void setLenght(double lenght) {
		this.lenght = lenght;
	}

	public int getEstimatedTimeInMinutes() {
		return estimatedTimeInMinutes;
	}

	public void setEstimatedTimeInMinutes(int estimatedTimeInMinutes) {
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
	}

	public Location getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(Location startLocation) {
		this.startLocation = startLocation;
	}

	public Location getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(Location endLocation) {
		this.endLocation = endLocation;
	}
    
}
