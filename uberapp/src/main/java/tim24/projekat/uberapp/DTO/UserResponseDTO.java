package tim24.projekat.uberapp.DTO;

import java.util.Base64;

import tim24.projekat.uberapp.model.User;

public class UserResponseDTO { //ovog usera vracas u responsu

	private Long id;
	private String name;
	private String surname;
	private String profilePicture;
	private String telephoneNumber;
	private String email;
	private String address;
	
	public UserResponseDTO()
	{
		super();
	}

	public UserResponseDTO(Long id, String name, String surname, String profilePicture, String telephoneNumber, String email,
			String address) {
		
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.profilePicture = profilePicture;
		this.telephoneNumber = telephoneNumber;
		this.email = email;
		this.address = address;
	}


	public UserResponseDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.surname = user.getSurname();
		if (user.getProfilePicture() != null) 
		{
			this.profilePicture = convertByteToString(user.getProfilePicture());
		}
		this.telephoneNumber = user.getTelephoneNumber();
		this.email=user.getEmail();
		this.address=user.getAddress();
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
	
	private String convertByteToString (byte[] bytes) 
	{
		//ovako se ovo ne radi lmao
		String encodedString = Base64.getEncoder().encodeToString(bytes);
		StringBuilder sb = new StringBuilder(encodedString);

		sb.insert(4, ":");
		sb.insert(15, ";");
		sb.insert(22, ",");

		String newString = sb.toString();
		return newString;
	}
	
	
}