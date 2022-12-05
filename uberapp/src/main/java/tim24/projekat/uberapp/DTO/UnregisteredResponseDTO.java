package tim24.projekat.uberapp.DTO;

public class UnregisteredResponseDTO {

	private Long estimatedTimeInMinutes;
	private Long estimatedCost;
	
	public UnregisteredResponseDTO(Long estimatedTimeInMinutes, Long estimatedCost) {
		super();
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
		this.estimatedCost = estimatedCost;
	}
	
	
}
/*{
  "estimatedTimeInMinutes": 10,
  "estimatedCost": 450
}*/