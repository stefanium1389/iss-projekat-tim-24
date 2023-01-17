package tim24.projekat.uberapp.DTO;


public class DriverDocumentRequestDTO { //otpakujes iz request body

	String name;
	String documentImage;
	Long driverId;
	
	public DriverDocumentRequestDTO(Long id, String name, String documentImage, Long driverId) {
		this.name = name;
		this.documentImage = documentImage;
		this.driverId = driverId;
	}
	
	public DriverDocumentRequestDTO() {
		super();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDocumentImage() {
		return documentImage;
	}
	public void setDocumentImage(String documentImage) {
		this.documentImage = documentImage;
	}
	public Long getDriverId() {
		return driverId;
	}
	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}
	@Override
	public String toString() {
		return "DriverDocument [name=" + name + ", documentImage=" + documentImage + ", driverId="
				+ driverId + "]";
	}
	
	
	
}
