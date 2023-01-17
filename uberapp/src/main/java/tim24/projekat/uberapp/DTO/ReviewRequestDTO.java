package tim24.projekat.uberapp.DTO;

public class ReviewRequestDTO {

	private int rating;
	private String comment;
	
	public ReviewRequestDTO(int rating, String comment) {
		super();
		this.rating = rating;
		this.comment = comment;
	}
	
	public ReviewRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
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
	
	
	
}
/*{
  "rating": 3,
  "comment": "The vehicle was bad and dirty"
}
 */