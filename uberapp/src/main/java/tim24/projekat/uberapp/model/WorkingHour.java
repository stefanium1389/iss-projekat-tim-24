package tim24.projekat.uberapp.model;
import java.time.Instant;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name = "working_hours")
@Entity
public class WorkingHour {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date startTime;
	private Date endTime;
	@OneToOne
	@JoinColumn(name = "driver_id")
	private User driver;
	
	public WorkingHour(Long id, Date start, Date end) {
		super();
		this.id = id;
		this.startTime = start;
		this.endTime = end;
	}

	public WorkingHour() {
		super();
	}
	
	public WorkingHour(String date, User driver) {
		
		super();
		/* "2023-01-12T16:30:34.507Z" */
		 Instant instant = Instant.parse(date);
		 Date parsedDate = Date.from(instant);
		this.startTime = parsedDate;
		this.endTime = parsedDate;
		this.driver = driver;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public User getDriver() {
		return driver;
	}

	public void setDriver(User driver) {
		this.driver = driver;
	}
	

}
