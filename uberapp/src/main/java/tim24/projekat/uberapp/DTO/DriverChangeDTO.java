package tim24.projekat.uberapp.DTO;

import tim24.projekat.uberapp.model.DriverUpdateDetails;

public class DriverChangeDTO {

	private Long id;
    private String name;
    private String surname;
    private String profilePicture;
    private String telephoneNumber;
    private String email;
    private String address;
    private String state;
    private boolean found;
    
	public DriverChangeDTO() {
		super();
	}

	public DriverChangeDTO(Long id ,String name, String surname, String profilePicture, String telephoneNumber,
			String email, String address) {
		super();
		this.id  = id;
		this.name = name;
		this.surname = surname;
		this.profilePicture = profilePicture;
		this.telephoneNumber = telephoneNumber;
		this.email = email;
		this.address = address;
	}

	public DriverChangeDTO(DriverUpdateDetails dud) {
		super();
		if (dud == null) {
			found = false;
		}
		else 
		{
			found = true;
			this.id = dud.getId();
			this.name = dud.getName();
			this.surname = dud.getSurname();
			this.profilePicture = dud.getProfilePictureAsString();
			this.telephoneNumber = dud.getTelephoneNumber();
			this.email = dud.getEmail();
			this.address = dud.getAddress();
			this.setState(dud.getUpdateState().toString());
		}
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isFound() {
		return found;
	}

	public void setFound(boolean found) {
		this.found = found;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
}
