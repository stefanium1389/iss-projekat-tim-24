package tim24.projekat.uberapp.DTO;

public class ReviewDTO {
	private Long id;
	private Long rating;
	private String comment;
	private UserRef passenger;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRating() {
		return rating;
	}
	public void setRating(Long rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public UserRef getPassenger() {
		return passenger;
	}
	public void setPassenger(UserRef passenger) {
		this.passenger = passenger;
	}
	
	public ReviewDTO(Long id, Long rating, String comment, UserRef passenger) {
		super();
		this.id = id;
		this.rating = rating;
		this.comment = comment;
		this.passenger = passenger;
	}
	
	public ReviewDTO() {
		super();
	}
	
}
