package tim24.projekat.uberapp.DTO;

public class UserDTO {

	private Long id;
	private String name;
	private String surname;
	private String profilePicture;
	private String telephoneNumber;
	private String email;
	private String address;
	
	
	public UserDTO(Long id, String name, String surname, String profilePicture, String telephoneNumber, String email,
			String address) {
		
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.profilePicture = profilePicture;
		this.telephoneNumber = telephoneNumber;
		this.email = email;
		this.address = address;
	}
	
	
	
	
}