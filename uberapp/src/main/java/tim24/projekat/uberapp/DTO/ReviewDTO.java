package tim24.projekat.uberapp.DTO;

import tim24.projekat.uberapp.model.Review;
import tim24.projekat.uberapp.model.ReviewDriver;
import tim24.projekat.uberapp.model.ReviewVehicle;

public class ReviewDTO {
	private Long id;
	private int rating;
	private String comment;
	private UserRef passenger;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
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
	
	public ReviewDTO(Long id, int rating, String comment, UserRef passenger) {
		super();
		this.id = id;
		this.rating = rating;
		this.comment = comment;
		this.passenger = passenger;
	}
	
	public ReviewDTO() {
		super();
	}
//	public ReviewDTO(ReviewVehicle rv) {
//		this.id = rv.getId();
//		this.rating = rv.getGrade();
//		this.comment = rv.getComment();
//		this.passenger = new UserRef(rv.getCommenter());
//		
//	}
//	public ReviewDTO(ReviewDriver rd) {
//		this.id = rd.getId();
//		this.rating = rd.getGrade();
//		this.comment = rd.getComment();
//		this.passenger = new UserRef(rd.getCommenter());
//	}
	public ReviewDTO(Review r) {
		this.id = r.getId();
		this.rating = r.getGrade();
		this.comment = r.getComment();
		this.passenger = new UserRef(r.getCommenter());
	}
	
}
