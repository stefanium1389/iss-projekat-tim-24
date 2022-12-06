package tim24.projekat.uberapp.DTO;


public class DriverDocumentDTO {
	
	Long id;
	String name;
	String documentImage;
	Long driverId;
	
	public DriverDocumentDTO(Long id, String name, String documentImage, Long driverId) {
		this.id = id;
		this.name = name;
		this.documentImage = documentImage;
		this.driverId = driverId;
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
		return "DriverDocument [id=" + id + ", name=" + name + ", documentImage=" + documentImage + ", driverId="
				+ driverId + "]";
	}
	
	
	
}
