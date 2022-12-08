package tim24.projekat.uberapp.DTO;

public class ReviewRequestDTO {

	private Long rating;
	private String comment;
	
	public ReviewRequestDTO(Long rating, String comment) {
		super();
		this.rating = rating;
		this.comment = comment;
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
/*{
  "rating": 3,
  "comment": "The vehicle was bad and dirty"
}
 */