package tim24.projekat.uberapp.model;

import jakarta.persistence.*;

@Table(name = "locations")
@Entity
public class Location
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private long id;
    private double geoWidth;
    private double geoHeight;
}
