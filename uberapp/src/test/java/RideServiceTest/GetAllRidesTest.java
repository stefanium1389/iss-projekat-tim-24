package RideServiceTest;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.model.*;
import tim24.projekat.uberapp.repo.RideRepository;
import tim24.projekat.uberapp.service.RideService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetAllRidesTest
{
    @Autowired
    @InjectMocks
    private RideService rideService;

    @Mock
    private RideRepository rideRepository;

    @Test
    @DisplayName("Should retrieve a DTOList<RideDTO> with two rides")
    public void testGetSome()
    {
        Date date = new Date();
        User user = new User();
        Refusal refusal = new Refusal(1L, "No reason.", date, user);
        List<User> passengers = new ArrayList<User>();
        passengers.add(user);
        passengers.add(user);
        Location location = new Location(1L, 10.5, 10.5, "Everywhere at the end of time...");
        Route route = new Route(1L, 10.5, 15, location, location);
        VehicleType vehicleType = new VehicleType();

        Ride ride1 = new Ride(1L, date, date, RideStatus.STARTED, false, false, false, user, refusal, passengers, route, date, 200, vehicleType);
        Ride ride2 = new Ride(2L, date, date, RideStatus.STARTED, false, false, false, user, refusal, passengers, route, date, 100, vehicleType);
        List<Ride> mockRides = new ArrayList<Ride>();
        mockRides.add(ride1);
        mockRides.add(ride2);

        RideDTO rideDTO1 = new RideDTO(ride1);
        RideDTO rideDTO2 = new RideDTO(ride2);
        DTOList<RideDTO> mockRideList = new DTOList<RideDTO>();
        mockRideList.add(rideDTO1);
        mockRideList.add(rideDTO2);

        Mockito.when(rideRepository.findAll()).thenReturn(mockRides);

        DTOList<RideDTO> actualRideList = rideService.getAllRides();
        //Assertions.assertThat(actualRideList.getResults()).isEqualTo(mockRideList.getResults());
        assertEquals(actualRideList.getTotalCount(), mockRideList.getTotalCount());
        List<RideDTO> actual = actualRideList.getResults();
        List<RideDTO> mock = mockRideList.getResults();
        for(int i = 0; i < actualRideList.getTotalCount(); i++)
        {
            assertEquals(actual.get(i).getId(), mock.get(i).getId());
        }
    }

    @Test
    @DisplayName("Should retrieve an empty DTOList<RideDTO>")
    public void testGetNone()
    {
        List<Ride> mockRides = new ArrayList<Ride>();

        Mockito.when(rideRepository.findAll()).thenReturn(mockRides);

        DTOList<RideDTO> actualRideList = rideService.getAllRides();
        assertEquals(actualRideList.getTotalCount(), 0);
    }

}
