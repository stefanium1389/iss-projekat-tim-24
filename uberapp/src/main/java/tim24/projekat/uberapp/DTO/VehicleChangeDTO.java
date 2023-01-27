package tim24.projekat.uberapp.DTO;

public class VehicleChangeDTO {
	/*export interface VehicleChangeDTO{
    driverId:number;
    vehicleType:string;
    model:string;
    licenseNumber:string;
    passengerSeats:number;
    babyTransport:boolean;
    petTransport:boolean;
}*/

	private Long driverId;
	private String vehicleType;
	private String model;
	private String licenceNumber;
	private int passengerSeats;
	private boolean babyTransport;
	private boolean petTransport;
	
	
	
	public VehicleChangeDTO() {
		super();
	}
	public VehicleChangeDTO(Long driverId, String vehicleType, String model, String licenceNumber, int passengerSeats,
			boolean babyTransport, boolean petTransport) {
		super();
		this.driverId = driverId;
		this.vehicleType = vehicleType;
		this.model = model;
		this.licenceNumber = licenceNumber;
		this.passengerSeats = passengerSeats;
		this.babyTransport = babyTransport;
		this.petTransport = petTransport;
	}
	public Long getDriverId() {
		return driverId;
	}
	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getLicenceNumber() {
		return licenceNumber;
	}
	public void setLicenceNumber(String licenceNumber) {
		this.licenceNumber = licenceNumber;
	}
	public int getPassengerSeats() {
		return passengerSeats;
	}
	public void setPassengerSeats(int passengerSeats) {
		this.passengerSeats = passengerSeats;
	}
	public boolean isBabyTransport() {
		return babyTransport;
	}
	public void setBabyTransport(boolean babyTransport) {
		this.babyTransport = babyTransport;
	}
	public boolean isPetTransport() {
		return petTransport;
	}
	public void setPetTransport(boolean petTransport) {
		this.petTransport = petTransport;
	}
	
	
	
}
