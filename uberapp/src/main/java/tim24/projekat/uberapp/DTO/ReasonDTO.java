package tim24.projekat.uberapp.DTO;

public class ReasonDTO
{
    private String reason;

    public ReasonDTO()
    {
        super();
    }

    public ReasonDTO(String reason)
    {
        super();
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String message) {
        this.reason = message;
    }
}
