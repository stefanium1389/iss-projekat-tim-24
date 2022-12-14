package tim24.projekat.uberapp.DTO;

public class Error {
	private String poruka;

	public Error(String poruka) {
		super();
		this.poruka = poruka;
	}

	public String getPoruka() {
		return poruka;
	}

	public void setPoruka(String poruka) {
		this.poruka = poruka;
	}
	
}
