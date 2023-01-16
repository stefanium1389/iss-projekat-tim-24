package tim24.projekat.uberapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import tim24.projekat.uberapp.DTO.DriverDocumentRequestDTO;
import jakarta.persistence.Id;

@Table(name="driver_documents")
@Entity
public class DriverDocument {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String name;
	String documentImage;
	Long driverId;

	public DriverDocument()
	{
		super();
	}

	public DriverDocument(Long id, String name, String documentImage, Long driverId) {
		super();
		this.id = id;
		this.name = name;
		this.documentImage = documentImage;
		this.driverId = driverId;
	}
	
	public DriverDocument(DriverDocumentRequestDTO ddrq, Long driverId) {
		super();
		this.name = ddrq.getName();
		this.documentImage = ddrq.getDocumentImage();
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
