package RideServiceTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.exception.InvalidRideStatusException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.*;
import tim24.projekat.uberapp.repo.RideRepository;
import tim24.projekat.uberapp.repo.VehicleRepository;
import tim24.projekat.uberapp.service.NotificationService;
import tim24.projekat.uberapp.service.RideService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class WithdrawRideTest
{
    @Autowired
    @InjectMocks
    private RideService rideService;

    @Mock
    private NotificationService notificationService;

    @Mock
    private RideRepository rideRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Test
    @DisplayName("Should retrieve a RideDTO with RideStatus.CANCELED")
    public void withdrawRideValid()
    {
        Date date = new Date();
        User user = new User(1L, "A", "B", new byte[]{}, "a", "a", "a", "a", true, false, Role.DRIVER);
        Refusal refusal = new Refusal(1L, "No reason.", date, user);
        List<User> passengers = new ArrayList<User>();
        passengers.add(user);
        passengers.add(user);
        Location location = new Location(1L, 10.5, 10.5, "Everywhere at the end of time...");
        Route route = new Route(1L, 10.5, 15, location, location);
        VehicleType vehicleType = new VehicleType(1L, "STANDARD", 100, 100);

        Ride mockRide = new Ride(1L, date, date, RideStatus.ACCEPTED, false, false, false, user, refusal, passengers, route, date, 200, vehicleType);

        Vehicle vehicle = new Vehicle(1l, "NS 420 RS", user, vehicleType, 5, false, false, location, "Ford Fiesta");

        Mockito.when(rideRepository.findById(1L)).thenReturn(Optional.of(mockRide));
        Mockito.when(vehicleRepository.findVehicleByDriverId(1L)).thenReturn(Optional.of(vehicle));

        RideDTO actualRideDTO = rideService.withdrawRide(1L);
        mockRide.setStatus(RideStatus.CANCELED);
        RideDTO mockRideDTO = new RideDTO(mockRide);

        assertEquals(actualRideDTO.getId(), mockRideDTO.getId());
        assertEquals(actualRideDTO.getStatus(), mockRideDTO.getStatus());
        assertEquals(mockRideDTO.getVehicleType(), actualRideDTO.getVehicleType());
    }

    @Test
    @DisplayName("Should throw an InvalidRideStatus exception")
    public void withdrawRideInvalid()
    {
        Date date = new Date();
        User user = new User(1L, "A", "B", new byte[]{}, "a", "a", "a", "a", true, false, Role.DRIVER);
        Refusal refusal = new Refusal(1L, "No reason.", date, user);
        List<User> passengers = new ArrayList<User>();
        passengers.add(user);
        passengers.add(user);
        Location location = new Location(1L, 10.5, 10.5, "Everywhere at the end of time...");
        Route route = new Route(1L, 10.5, 15, location, location);
        VehicleType vehicleType = new VehicleType(1L, "STANDARD", 100, 100);

        Ride mockRide = new Ride(1L, date, date, RideStatus.STARTED, false, false, false, user, refusal, passengers, route, date, 200, vehicleType);

        Vehicle vehicle = new Vehicle(1l, "NS 420 RS", user, vehicleType, 5, false, false, location, "Ford Fiesta");

        Mockito.when(rideRepository.findById(1L)).thenReturn(Optional.of(mockRide));

        assertThrows(InvalidRideStatusException.class, () -> rideService.withdrawRide(1L));
    }

    @Test
    @DisplayName("Should throw an ObjectNotFound exception for no ride")
    public void withdrawRideNoRide()
    {
        Date date = new Date();
        User user = new User(1L, "A", "B", new byte[]{}, "a", "a", "a", "a", true, false, Role.DRIVER);
        Refusal refusal = new Refusal(1L, "No reason.", date, user);
        List<User> passengers = new ArrayList<User>();
        passengers.add(user);
        passengers.add(user);
        Location location = new Location(1L, 10.5, 10.5, "Everywhere at the end of time...");
        Route route = new Route(1L, 10.5, 15, location, location);
        VehicleType vehicleType = new VehicleType(1L, "STANDARD", 100, 100);

        Ride mockRide = new Ride(1L, date, date, RideStatus.ACCEPTED, false, false, false, user, refusal, passengers, route, date, 200, vehicleType);

        Vehicle vehicle = new Vehicle(1l, "NS 420 RS", user, vehicleType, 5, false, false, location, "Ford Fiesta");

        Mockito.when(rideRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> rideService.withdrawRide(1L));
    }

    @Test
    @DisplayName("Should throw an ObjectNotFound exception for no vehicle")
    public void withdrawRideNoVehicle()
    {
        Date date = new Date();
        User user = new User(1L, "A", "B", new byte[]{}, "a", "a", "a", "a", true, false, Role.DRIVER);
        Refusal refusal = new Refusal(1L, "No reason.", date, user);
        List<User> passengers = new ArrayList<User>();
        passengers.add(user);
        passengers.add(user);
        Location location = new Location(1L, 10.5, 10.5, "Everywhere at the end of time...");
        Route route = new Route(1L, 10.5, 15, location, location);
        VehicleType vehicleType = new VehicleType(1L, "STANDARD", 100, 100);

        Ride mockRide = new Ride(1L, date, date, RideStatus.ACCEPTED, false, false, false, user, refusal, passengers, route, date, 200, vehicleType);

        Vehicle vehicle = new Vehicle(1l, "NS 420 RS", user, vehicleType, 5, false, false, location, "Ford Fiesta");

        Mockito.when(rideRepository.findById(1L)).thenReturn(Optional.of(mockRide));
        Mockito.when(vehicleRepository.findVehicleByDriverId(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> rideService.withdrawRide(1L));
    }
}
