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

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public UserRef getUser()
    {
        return this.user;
    }

    public void set(UserRef user)
    {
        this.user = user;
    }

    public RideDTO getRide()
    {
        return this.ride;
    }

    public void set(RideDTO ride)
    {
        this.ride = ride;
    }

    public String getTime()
    {
        return this.time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getReason()
    {
        return this.reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }
}