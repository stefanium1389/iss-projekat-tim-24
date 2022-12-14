package tim24.projekat.uberapp.model;

import jakarta.persistence.*;

@Table(name = "vehicle_details")
@Entity
public class VehicleDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private long id;
    private String brand;
    private String model;
}
