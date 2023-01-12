package tim24.projekat.uberapp.model;

import jakarta.persistence.*;

@Table(name = "vehicle_type")
@Entity
public class VehicleType
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;
    private String typeName;
    private int startingPrice;
    private int pricePerKm;

    public VehicleType()
    {
        super();
    }

	public VehicleType(Long id, String typeName, int startingPrice, int pricePerKm) {
		super();
		this.id = id;
		this.typeName = typeName;
		this.startingPrice = startingPrice;
		this.pricePerKm = pricePerKm;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getStartingPrice() {
		return startingPrice;
	}

	public void setStartingPrice(int startingPrice) {
		this.startingPrice = startingPrice;
	}

	public int getPricePerKm() {
		return pricePerKm;
	}

	public void setPricePerKm(int pricePerKm) {
		this.pricePerKm = pricePerKm;
	}
    
}
