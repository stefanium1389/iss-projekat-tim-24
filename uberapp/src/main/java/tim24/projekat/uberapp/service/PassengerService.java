package tim24.projekat.uberapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.DTO.UserResponseDTO;
import tim24.projekat.uberapp.repo.RideRepository;
import tim24.projekat.uberapp.repo.UserRepository;

@Service
public class PassengerService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired 
	private RideRepository rideRepo;

	public UserResponseDTO postPassenger() {
		return new UserResponseDTO();
	}

	public DTOList<UserResponseDTO> getPassengers()
	{
		DTOList<UserResponseDTO> list = new DTOList<UserResponseDTO>();
        UserResponseDTO user = new UserResponseDTO();
        list.add(user);
        return list;
	}

	public void activatePassenger(Long id)
	{
		//TODO
	}

	public UserResponseDTO getPassenger(Long id) {
		  return new UserResponseDTO();
	}

	public UserResponseDTO updatePassenger(Long id) {
		return new UserResponseDTO();
	}

	public DTOList<RideDTO> getPassengerRides(Long id, int page, int size, String sort, String from, String to)
	{
		DTOList<RideDTO> list = new DTOList<>();
        RideDTO ride = new RideDTO();
        list.add(ride);
        return list;
	}

}
