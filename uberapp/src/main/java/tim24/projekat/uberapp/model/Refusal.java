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

	public Refusal(Long id, String reason, Date time, User user) {
		super();
		this.id = id;
		this.reason = reason;
		this.time = time;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

//	public void setId(Long id) {
//		this.id = id;
//	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
}
