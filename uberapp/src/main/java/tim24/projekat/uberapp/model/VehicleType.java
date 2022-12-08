package tim24.projekat.uberapp.model;

import jakarta.persistence.*;

@Table(name = "vehicle details")
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
}
