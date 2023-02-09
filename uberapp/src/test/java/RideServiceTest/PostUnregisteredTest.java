package RideServiceTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import tim24.projekat.uberapp.DTO.*;
import tim24.projekat.uberapp.exception.ConditionNotMetException;
import tim24.projekat.uberapp.exception.InvalidArgumentException;
import tim24.projekat.uberapp.model.*;
import tim24.projekat.uberapp.repo.VehicleTypeRepository;
import tim24.projekat.uberapp.service.RideService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PostUnregisteredTest
{
    @Autowired
    @InjectMocks
    private RideService rideService;

    @Mock
    private VehicleTypeRepository vehicleTypeRepository;

    @Test
    @DisplayName("Should retrieve an UnregisteredResponseDTO")
    public void postUnregisteredValid()
    {
        Date date = new Date();
        User user = new User(1L, "A", "B", new byte[]{}, "a", "a", "a", "a", true, false, Role.DRIVER);
        Refusal refusal = new Refusal(1L, "No reason.", date, user);
        List<User> passengers = new ArrayList<User>();
        passengers.add(user);
        passengers.add(user);
        Location location1 = new Location(1L, 10.5, 10.5, "Everywhere at the end of time...");
        Location location2 = new Location(2L, 20.5, 10.5, "Everywhere at the end of time...");
        Route route = new Route(1L, 10.5, 15, location1, location2);
        VehicleType vehicleType = new VehicleType(1L, "STANDARD", 100, 100);

        RouteDTO routeDTO = new RouteDTO(route);
        List< RouteDTO > locations = new ArrayList<>();
        locations.add(routeDTO);

        UnregisteredRequestDTO unregisteredRequestDTO = new UnregisteredRequestDTO(locations, "STANDARD", false, false);

        Mockito.when(vehicleTypeRepository.findByTypeName("STANDARD")).thenReturn(Optional.of(vehicleType));
        Mockito.when(vehicleTypeRepository.findOneByTypeName("STANDARD")).thenReturn(Optional.of(vehicleType));

        UnregisteredResponseDTO unregisteredResponseDTO = rideService.postUnregistered(unregisteredRequestDTO);

        assertEquals(unregisteredResponseDTO.getEstimatedCost(), 218290);
        assertEquals(unregisteredResponseDTO.getEstimatedTimeInMinutes(), 2930);
    }

    @Test
    @DisplayName("Should throw an InvalidArgumentException exception for no vehicle type")
    public void postUnregisteredInvalidVehicleType()
    {
        Date date = new Date();
        User user = new User(1L, "A", "B", new byte[]{}, "a", "a", "a", "a", true, false, Role.DRIVER);
        Refusal refusal = new Refusal(1L, "No reason.", date, user);
        List<User> passengers = new ArrayList<User>();
        passengers.add(user);
        passengers.add(user);
        Location location1 = new Location(1L, 10.5, 10.5, "Everywhere at the end of time...");
        Location location2 = new Location(2L, 20.5, 10.5, "Everywhere at the end of time...");
        Route route = new Route(1L, 10.5, 15, location1, location2);
        VehicleType vehicleType = new VehicleType(1L, "STANDARD", 100, 100);

        RouteDTO routeDTO = new RouteDTO(route);
        List< RouteDTO > locations = new ArrayList<>();
        locations.add(routeDTO);

        UnregisteredRequestDTO unregisteredRequestDTO = new UnregisteredRequestDTO(locations, "STANDARD", false, false);

        Mockito.when(vehicleTypeRepository.findByTypeName("STANDARD")).thenReturn(Optional.empty());

        assertThrows(InvalidArgumentException.class, () -> rideService.postUnregistered(unregisteredRequestDTO));
    }

    @Test
    @DisplayName("Should throw an InvalidArgumentException exception for no locations")
    public void postUnregisteredInvalidLocations()
    {
        Date date = new Date();
        User user = new User(1L, "A", "B", new byte[]{}, "a", "a", "a", "a", true, false, Role.DRIVER);
        Refusal refusal = new Refusal(1L, "No reason.", date, user);
        List<User> passengers = new ArrayList<User>();
        passengers.add(user);
        passengers.add(user);
        Location location1 = new Location(1L, 10.5, 10.5, "Everywhere at the end of time...");
        Location location2 = new Location(2L, 20.5, 10.5, "Everywhere at the end of time...");
        Route route = new Route(1L, 10.5, 15, location1, location2);
        VehicleType vehicleType = new VehicleType(1L, "STANDARD", 100, 100);

        RouteDTO routeDTO = new RouteDTO(route);
        List< RouteDTO > locations = new ArrayList<>();

        UnregisteredRequestDTO unregisteredRequestDTO = new UnregisteredRequestDTO(locations, "STANDARD", false, false);

        Mockito.when(vehicleTypeRepository.findByTypeName("STANDARD")).thenReturn(Optional.of(vehicleType));

        assertThrows(InvalidArgumentException.class, () -> rideService.postUnregistered(unregisteredRequestDTO));
    }
}
