package tim24.projekat.uberapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim24.projekat.uberapp.DTO.PanicDTO;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.repo.RideRepository;

@Service
public class RideService
{
	@Autowired
	private RideRepository rideRepo;

	public RideDTO postRide()
	{
		RideDTO ride = new RideDTO();
		return ride;
	}

	public RideDTO getDriverRide(Long id)
	{
        RideDTO ride = new RideDTO();
		return ride;
	}

	public RideDTO getPassengerRide(Long id)
	{
		RideDTO ride = new RideDTO();
		return ride;
	}

	public RideDTO getRides(Long id)
	{
		RideDTO ride = new RideDTO();
		return ride;
	}

	public RideDTO withdrawRide(Long id)
	{
		RideDTO ride = new RideDTO();
		return ride;
	}

	public PanicDTO panicRide(Long id)
	{
		 PanicDTO panic = new PanicDTO();
		 return panic;
	}

	public RideDTO acceptRide(Long id)
	{
		RideDTO ride = new RideDTO();
		return ride;
	}

	public RideDTO endRide(Long id)
	{
		 RideDTO ride = new RideDTO();
		 return ride;
	}

	public RideDTO cancelRide(Long id)
	{
		RideDTO ride = new RideDTO();
		return ride;
	}
}