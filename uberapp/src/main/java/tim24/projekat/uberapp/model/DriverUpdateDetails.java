package tim24.projekat.uberapp.model;

import java.util.Base64;
import java.util.Date;

import jakarta.persistence.*;
import tim24.projekat.uberapp.DTO.GeoCoordinateDTO;
import tim24.projekat.uberapp.DTO.UserUpdateRequestDTO;

@Table(name = "driver_update_details")
@Entity
public class DriverUpdateDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;
    private String name;
    private String surname;
    @Lob
    private byte[] profilePicture;
    private String telephoneNumber;
    private String email;
    private String address;
    private Date dateOfSubmission;
    @Enumerated(EnumType.STRING)
    private UpdateState updateState;
    @OneToOne
    private User forDriver;
    
	public DriverUpdateDetails() {
		super();
	}

	public DriverUpdateDetails(Long id, String name, String surname, byte[] profilePicture, String telephoneNumber,
			String email, String address, Date dateOfSubmission, UpdateState updateState, User forDriver) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.profilePicture = profilePicture;
		this.telephoneNumber = telephoneNumber;
		this.email = email;
		this.address = address;
		this.dateOfSubmission = dateOfSubmission;
		this.updateState = updateState;
		this.setForDriver(forDriver);
	}



	public DriverUpdateDetails(User driver) {
		super();
		this.name = driver.getName();
		this.surname = driver.getSurname();
		this.profilePicture = driver.getProfilePicture();
		this.telephoneNumber = driver.getTelephoneNumber();
		this.email = driver.getEmail();
		this.address = driver.getAddress();
		this.dateOfSubmission = new Date();
		this.updateState = UpdateState.PENDING;
		this.setForDriver(driver);
	}

	public DriverUpdateDetails(UserUpdateRequestDTO updatedDriver, User driver) {
		super();
		this.name = updatedDriver.getName();
		this.surname = updatedDriver.getSurname();
		this.profilePicture = convertToByte(updatedDriver.getProfilePicture());
		this.telephoneNumber = updatedDriver.getTelephoneNumber();
		this.email = updatedDriver.getEmail();
		this.address = updatedDriver.getAddress();
		this.dateOfSubmission = new Date();
		this.updateState = UpdateState.PENDING;
		this.setForDriver(driver);
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

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}
	
	public String getProfilePictureAsString() {
		if (this.profilePicture == null)
			return null;
		String encodedString = Base64.getEncoder().encodeToString(this.profilePicture);
		StringBuilder sb = new StringBuilder(encodedString);

		sb.insert(4, ":");
		sb.insert(15, ";");
		sb.insert(22, ",");

		String newString = sb.toString();
		return encodedString;
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

	public Date getDateOfSubmission() {
		return dateOfSubmission;
	}

	public void setDateOfSubmission(Date dateOfSubmission) {
		this.dateOfSubmission = dateOfSubmission;
	}

	public UpdateState getUpdateState() {
		return updateState;
	}

	public void setUpdateState(UpdateState updateState) {
		this.updateState = updateState;
	}



	public User getForDriver() {
		return forDriver;
	}



	public void setForDriver(User forDriver) {
		this.forDriver = forDriver;
	}
	
	private byte[] convertToByte(String string) 
	{
		byte[] decodedBytes = Base64.getMimeDecoder().decode(string);
		
			
		return decodedBytes;
	}
    
    
    
}
