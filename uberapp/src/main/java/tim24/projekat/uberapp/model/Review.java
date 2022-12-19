package tim24.projekat.uberapp.model;

import jakarta.persistence.*;

@Table(name = "reviews")
@Entity
public class Review
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;
    private int driverGrade;
    private int vehicleGrade;
    private String comment;

    public Review()
    {
        super();
    }
}
