package RideServiceTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import tim24.projekat.uberapp.DTO.PanicDTO;
import tim24.projekat.uberapp.DTO.ReasonDTO;
import tim24.projekat.uberapp.DTO.RideDTO;
import tim24.projekat.uberapp.exception.ConditionNotMetException;
import tim24.projekat.uberapp.exception.InvalidRideStatusException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.*;
import tim24.projekat.uberapp.repo.PanicRepository;
import tim24.projekat.uberapp.repo.RideRepository;
import tim24.projekat.uberapp.repo.UserRepository;
import tim24.projekat.uberapp.repo.VehicleRepository;
import tim24.projekat.uberapp.service.NotificationService;
import tim24.projekat.uberapp.service.RideService;
import tim24.projekat.uberapp.service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PanicRideTest
{
    @Autowired
    @InjectMocks
    private RideService rideService;

    @Mock
    private NotificationService notificationService;

    @Mock
    private UserService userService;

    @Mock
    private RideRepository rideRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private PanicRepository panicRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Should retrieve a PanicDTO")
    public void cancelRideValid()
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

        Mockito.when(userService.findUserByEmail("a")).thenReturn(user);
        Mockito.when(rideRepository.findRideById(1L)).thenReturn(Optional.of(mockRide));
        Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<User>());

        PanicDTO actualPanicDTO = rideService.panicRide(1L, new ReasonDTO("reason"), "a");

        assertEquals(actualPanicDTO.getReason(), "reason");
    }

    @Test
    @DisplayName("Should throw an ConditionNotMetException exception for wrong RideStatus")
    public void cancelRideInvalid1()
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

        Ride mockRide = new Ride(1L, date, date, RideStatus.FINISHED, false, false, false, user, refusal, passengers, route, date, 200, vehicleType);

        Vehicle vehicle = new Vehicle(1l, "NS 420 RS", user, vehicleType, 5, false, false, location, "Ford Fiesta");

        Mockito.when(userService.findUserByEmail("a")).thenReturn(user);
        Mockito.when(rideRepository.findRideById(1L)).thenReturn(Optional.of(mockRide));

        assertThrows(ConditionNotMetException.class, () -> rideService.panicRide(1L, new ReasonDTO("reason"), "a"));
    }

    @Test
    @DisplayName("Should throw an ConditionNotMetException exception for not being in ride")
    public void cancelRideInvalid2()
    {
        Date date = new Date();
        User user = new User(1L, "A", "B", new byte[]{}, "a", "a", "a", "a", true, false, Role.DRIVER);
        User user2 = new User(2L, "A", "B", new byte[]{}, "a", "a", "a", "a", true, false, Role.DRIVER);
        Refusal refusal = new Refusal(1L, "No reason.", date, user);
        List<User> passengers = new ArrayList<User>();
        passengers.add(user2);
        passengers.add(user2);
        Location location = new Location(1L, 10.5, 10.5, "Everywhere at the end of time...");
        Route route = new Route(1L, 10.5, 15, location, location);
        VehicleType vehicleType = new VehicleType(1L, "STANDARD", 100, 100);

        Ride mockRide = new Ride(1L, date, date, RideStatus.FINISHED, false, false, false, user2, refusal, passengers, route, date, 200, vehicleType);

        Vehicle vehicle = new Vehicle(1l, "NS 420 RS", user, vehicleType, 5, false, false, location, "Ford Fiesta");

        Mockito.when(userService.findUserByEmail("a")).thenReturn(user);
        Mockito.when(rideRepository.findRideById(1L)).thenReturn(Optional.of(mockRide));

        assertThrows(ConditionNotMetException.class, () -> rideService.panicRide(1L, new ReasonDTO("reason"), "a"));
    }
}
