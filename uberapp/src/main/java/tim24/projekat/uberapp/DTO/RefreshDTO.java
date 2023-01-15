package tim24.projekat.uberapp.DTO;

public class RefreshDTO {	
	
	private String refreshToken;

	public RefreshDTO(String refreshToken) {
		super();
		this.refreshToken = refreshToken;
	}

	public RefreshDTO() {
		super();
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	

}
