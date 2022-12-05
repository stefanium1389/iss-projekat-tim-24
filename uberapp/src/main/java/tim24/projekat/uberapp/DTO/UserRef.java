package tim24.projekat.uberapp.DTO;

public class UserRef {
	
	private Long id;
	private String email;
	private String type;
	public UserRef(Long id, String email, String type) {
		super();
		this.id = id;
		this.email = email;
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
