package tim24.projekat.uberapp.DTO;

public class PanicDTO
{
    Long id;
    UserRef user;
    RideDTO ride;
    String time;
    String reason;

    public PanicDTO()
    {
        super();
        /*this.id = 0L;
        this.user = new UserRef(0L, "d@gmail.com");
        this.ride = new RideDTO();
        this.time = "fafa";
        this.reason = "gugu";*/
    }
}