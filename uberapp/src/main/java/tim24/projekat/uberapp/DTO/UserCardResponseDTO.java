package tim24.projekat.uberapp.DTO;

import java.util.Base64;

import tim24.projekat.uberapp.model.User;

public class UserCardResponseDTO { //ovog usera vracas u responsu

	private Long id;
	private String name;
	private String surname;
	private String profilePicture;
	private String role;
	private String email;
	
	public UserCardResponseDTO()
	{
		super();
	}

	public UserCardResponseDTO(Long id, String name, String surname, String profilePicture, String role, String email) {
		
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.profilePicture = profilePicture;
		this.setRole(role);
		this.email = email;
	}


	public UserCardResponseDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.surname = user.getSurname();
		if (user.getProfilePicture() != null) 
		{
			this.profilePicture = convertByteToString(user.getProfilePicture());
		}
		this.setRole(user.getRole().toString());
		this.email = user.getEmail();
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}