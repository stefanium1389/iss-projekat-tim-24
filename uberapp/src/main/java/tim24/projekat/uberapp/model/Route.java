package tim24.projekat.uberapp.model;

import jakarta.persistence.*;

@Table(name = "routes")
@Entity
public class Route
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private long id;
    private double lenght; //in km
    @OneToOne
    private Location start;
    @OneToOne
    private Location end;
}
