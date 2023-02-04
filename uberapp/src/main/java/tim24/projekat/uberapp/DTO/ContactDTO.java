package tim24.projekat.uberapp.DTO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ContactDTO {

    String picture;
    String startAddress;
    String endAddress;
    Long rideId;
    Long otherUserId;
    String rideStartDate;

    public ContactDTO(String picture, String startAddress, String endAddress, Long rideId, Long otherUserId, String rideStartDate) {
        this.picture = picture;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.rideId = rideId;
        this.otherUserId = otherUserId;
        this.rideStartDate = rideStartDate;
    }

    public ContactDTO(String profilePictureAsString, String startAddress, String endAddress, Long rideId, Long otherId,
			Date startTime) {
    	this.picture = profilePictureAsString;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.rideId = rideId;
        this.otherUserId = otherId;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.rideStartDate = sdf.format(startTime);
	}

	public String getPicture() {
        return picture;
    }

    public ContactDTO setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public ContactDTO setStartAddress(String startAddress) {
        this.startAddress = startAddress;
        return this;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public ContactDTO setEndAddress(String endAddress) {
        this.endAddress = endAddress;
        return this;
    }

    public Long getRideId() {
        return rideId;
    }

    public ContactDTO setRideId(Long rideId) {
        this.rideId = rideId;
        return this;
    }

    public Long getOtherUserId() {
        return otherUserId;
    }

    public ContactDTO setOtherUserId(Long otherUserId) {
        this.otherUserId = otherUserId;
        return this;
    }

    public String getRideStartDate() {
        return rideStartDate;
    }

    public ContactDTO setRideStartDate(String rideStartDate) {
        this.rideStartDate = rideStartDate;
        return this;
    }
}
