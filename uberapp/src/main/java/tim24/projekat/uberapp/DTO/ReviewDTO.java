package tim24.projekat.uberapp.DTO;

public class ReviewDTO {
	private Long id;
	private Long rating;
	private String comment;
	
	public ReviewDTO(Long id, Long rating, String comment) {
		super();
		this.id = id;
		this.rating = rating;
		this.comment = comment;
	}
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
	
	
}
/*"id": 123,
    "rating": 3,
    "comment": "The vehicle was bad and dirty"*/