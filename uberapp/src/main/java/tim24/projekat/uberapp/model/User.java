package tim24.projekat.uberapp.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import tim24.projekat.uberapp.DTO.UserRegistrationDTO;
import tim24.projekat.uberapp.DTO.UserRequestDTO;
import tim24.projekat.uberapp.DTO.UserUpdateRequestDTO;

@Table(name="users")
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false, unique = true)
	private Long id;
	private String name;
	private String surname;
	private String profilePicture;
	private String telephoneNumber;
	private String email;
	private String address;
	private String password;
	private boolean activated;
	private boolean blocked;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Transient
	private String accessToken;
	
	public User() {
		super();
	}

	public User(Long id, String name, String surname, String profilePicture, String telephoneNumber, String email,
			String address, String password, boolean activated, boolean blocked, Role role) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.profilePicture = profilePicture;
		this.telephoneNumber = telephoneNumber;
		this.email = email;
		this.address = address;
		this.password = password;
		this.activated = activated;
		this.blocked = blocked;
		this.role = role;
	}

	public User(UserRegistrationDTO dto, String password) {
		this.name = dto.getName();
		this.surname = dto.getSurname();
		this.profilePicture = dto.getProfilePicture();
		this.telephoneNumber = dto.getTelephoneNumber();
		this.email = dto.getEmail();
		this.password = password;
		this.address = dto.getAddress();
		this.activated = false;
		this.blocked = false;
		this.role = Role.USER;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", surname=" + surname + ", profilePicture=" + profilePicture
				+ ", telephoneNumber=" + telephoneNumber + ", email=" + email + ", address=" + address + ", password="
				+ password + ", role=" + role + ", accessToken=" + accessToken + "]";
	}

	public void update(UserUpdateRequestDTO updated) {
		this.setEmail(updated.getEmail());
		this.setAddress(updated.getAddress());
		this.setName(updated.getName());
		this.setSurname(updated.getSurname());
		this.setTelephoneNumber(updated.getTelephoneNumber());
		this.setProfilePicture(updated.getProfilePicture());
	}
	
}
