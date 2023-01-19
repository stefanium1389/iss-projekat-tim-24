package tim24.projekat.uberapp.DTO;

public class WorkingHourDTO {
	private Long id;
	private String start;
	private String end;
	
	public WorkingHourDTO(Long id, String start, String end) {
		super();
		this.id = id;
		this.start = start;
		this.end = end;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	@Override
	public String toString() {
		return "WorkingHour [id=" + id + ", start=" + start + ", end=" + end + "]";
	}
	
	
}
