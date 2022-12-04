package tim24.projekat.uberapp.model;

import jakarta.persistence.*;

@Table(name = "vehicles")
@Entity
public class Vehicle
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private long id;
    private String regPlates;
    private int numberOfSeats;
    private boolean allowedBabyInVehicle;
    private boolean allowedPetInVehicle;
}
