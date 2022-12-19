package tim24.projekat.uberapp.model;

import jakarta.persistence.*;

import java.util.Date;

@Table(name = "refusals")
@Entity
public class Refusal
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;
    private String reason;
    private Date time;
    @OneToOne
    private User user;

    public Refusal()
    {
        super();
    }
}
