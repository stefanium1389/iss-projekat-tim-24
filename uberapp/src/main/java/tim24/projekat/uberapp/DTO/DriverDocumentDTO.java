package tim24.projekat.uberapp.DTO;

import tim24.projekat.uberapp.model.DriverDocument;

public class DriverDocumentDTO { //saljes u response
	
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
	
	public DriverDocumentDTO(DriverDocument d) {
		this.id = d.getId();
		this.name = d.getName();
		this.documentImage = d.getDocumentImage();
		this.driverId = d.getDriverId();
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
