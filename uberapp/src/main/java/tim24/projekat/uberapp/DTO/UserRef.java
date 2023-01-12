package tim24.projekat.uberapp.DTO;

import tim24.projekat.uberapp.model.User;

public class UserRef {
	
	private Long id;
	private String email;
	public UserRef()
	{
		super();
	}
	public UserRef(Long id, String email) {
		super();
		this.id = id;
		this.email = email;
	}
	public UserRef(User user) {
		super();
		this.id=user.getId();
		this.email=user.getEmail();
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
	
}
