package tim24.projekat.uberapp.DTO;

public class UnregisteredResponseDTO {

	private Long estimatedTimeInMinutes;
	private Long estimatedCost;
	
	public UnregisteredResponseDTO(Long estimatedTimeInMinutes, Long estimatedCost) {
		super();
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
		this.estimatedCost = estimatedCost;
	}

	public Long getEstimatedTimeInMinutes() {
		return estimatedTimeInMinutes;
	}

	public void setEstimatedTimeInMinutes(Long estimatedTimeInMinutes) {
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
	}

	public Long getEstimatedCost() {
		return estimatedCost;
	}

	public void setEstimatedCost(Long estimatedCost) {
		this.estimatedCost = estimatedCost;
	}
	
}
