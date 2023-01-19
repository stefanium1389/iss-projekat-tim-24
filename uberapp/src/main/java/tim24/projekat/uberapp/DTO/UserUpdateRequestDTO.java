package tim24.projekat.uberapp.DTO;

import jakarta.persistence.Lob;

public class UserUpdateRequestDTO { //ovog usera otpakujes iz request body

	private String name;
	private String surname;
	@Lob
	private String profilePicture;
	private String telephoneNumber;
	private String email;
	private String address;
	
	public UserUpdateRequestDTO(String name, String surname, String profilePicture, String telephoneNumber, String email,
			String address) {
		super();
		this.name = name;
		this.surname = surname;
		this.profilePicture = profilePicture;
		this.telephoneNumber = telephoneNumber;
		this.email = email;
		this.address = address;
	}
	
	public UserUpdateRequestDTO() {
		super();
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
}