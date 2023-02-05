package tim24.projekat.uberapp.DTO;

public class SpecificMessagesRequestIDsDTO {

    private Long userId;
    private Long otherId;
    private Long rideId;

    public SpecificMessagesRequestIDsDTO(Long userId, Long otherId, Long rideId) {
        this.userId = userId;
        this.otherId = otherId;
        this.rideId = rideId;
    }

    public SpecificMessagesRequestIDsDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public SpecificMessagesRequestIDsDTO setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getOtherId() {
        return otherId;
    }

    public SpecificMessagesRequestIDsDTO setOtherId(Long otherId) {
        this.otherId = otherId;
        return this;
    }

    public Long getRideId() {
        return rideId;
    }

    public SpecificMessagesRequestIDsDTO setRideId(Long rideId) {
        this.rideId = rideId;
        return this;
    }
}
