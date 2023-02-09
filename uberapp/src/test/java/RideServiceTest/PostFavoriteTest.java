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
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.*;
import tim24.projekat.uberapp.repo.*;
import tim24.projekat.uberapp.service.RideService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PostFavoriteTest
{
    @Autowired
    @InjectMocks
    private RideService rideService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FavoriteRideRepository favoriteRideRepository;

    @Mock
    private VehicleTypeRepository vehicleTypeRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private RouteRepository routeRepository;

    @Test
    @DisplayName("Should retrieve a PanicDTO")
    public void PostFavoriteValid()
    {
        Date date = new Date();
        User user = new User(1L, "A", "B", new byte[]{}, "a", "a", "a", "a", true, false, Role.DRIVER);
        Refusal refusal = new Refusal(1L, "No reason.", date, user);
        List<User> passengers = new ArrayList<User>();
        passengers.add(user);
        passengers.add(user);
        List<UserRef> passengers1 = new ArrayList<>();
        passengers1.add(new UserRef(user));
        passengers1.add(new UserRef(user));
        Location location = new Location(1L, 10.5, 10.5, "Everywhere at the end of time...");
        Route route = new Route(1L, 10.5, 15, location, location);
        RouteDTO routeDTO = new RouteDTO(route);
        List<RouteDTO> list1389 = new ArrayList<>();
        list1389.add(routeDTO);
        VehicleType vehicleType = new VehicleType(1L, "STANDARD", 100, 100);

        Ride mockRide = new Ride(1L, date, date, RideStatus.STARTED, false, false, false, user, refusal, passengers, route, date, 200, vehicleType);
        FavoriteRide mockFavoriteRide = new FavoriteRide(1L, "Ime", false, false, route, user, "STANDARD");
        FavoriteRideDTO mockFavoriteRideDTO = new FavoriteRideDTO("Ime", list1389, passengers1, "STANDARD", false, false);
        List<FavoriteRide> FRList = new ArrayList<>();
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);

        Vehicle vehicle = new Vehicle(1l, "NS 420 RS", user, vehicleType, 5, false, false, location, "Ford Fiesta");

        Mockito.when(userRepository.findUserByEmail("a")).thenReturn(Optional.of(user));
        Mockito.when(favoriteRideRepository.findAllFavoriteRideByPassengerId(1L)).thenReturn(FRList);
        Mockito.when(vehicleTypeRepository.findByTypeName("STANDARD")).thenReturn(Optional.of(vehicleType));

        FavoriteRideResponseDTO actualFavoriteRideResponseDTO = rideService.postFavorite("a", mockFavoriteRideDTO);

        assertEquals(actualFavoriteRideResponseDTO.getFavoriteName(), "Ime");
    }

    @Test
    @DisplayName("Should throw aн ObjectNotFoundException exception")
    public void PostFavoriteInvalid1()
    {
        Date date = new Date();
        User user = new User(1L, "A", "B", new byte[]{}, "a", "a", "a", "a", true, false, Role.DRIVER);
        Refusal refusal = new Refusal(1L, "No reason.", date, user);
        List<User> passengers = new ArrayList<User>();
        passengers.add(user);
        passengers.add(user);
        List<UserRef> passengers1 = new ArrayList<>();
        passengers1.add(new UserRef(user));
        passengers1.add(new UserRef(user));
        Location location = new Location(1L, 10.5, 10.5, "Everywhere at the end of time...");
        Route route = new Route(1L, 10.5, 15, location, location);
        RouteDTO routeDTO = new RouteDTO(route);
        List<RouteDTO> list1389 = new ArrayList<>();
        list1389.add(routeDTO);
        VehicleType vehicleType = new VehicleType(1L, "STANDARD", 100, 100);

        Ride mockRide = new Ride(1L, date, date, RideStatus.STARTED, false, false, false, user, refusal, passengers, route, date, 200, vehicleType);
        FavoriteRide mockFavoriteRide = new FavoriteRide(1L, "Ime", false, false, route, user, "STANDARD");
        FavoriteRideDTO mockFavoriteRideDTO = new FavoriteRideDTO("Ime", list1389, passengers1, "STANDARD", false, false);
        List<FavoriteRide> FRList = new ArrayList<>();
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);

        Vehicle vehicle = new Vehicle(1l, "NS 420 RS", user, vehicleType, 5, false, false, location, "Ford Fiesta");

        Mockito.when(userRepository.findUserByEmail("a")).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> rideService.postFavorite("a", mockFavoriteRideDTO));
    }

    @Test
    @DisplayName("Should throw aн InvalidArgumentException exception for too many favorite rides")
    public void PostFavoriteInvalid2()
    {
        Date date = new Date();
        User user = new User(1L, "A", "B", new byte[]{}, "a", "a", "a", "a", true, false, Role.DRIVER);
        Refusal refusal = new Refusal(1L, "No reason.", date, user);
        List<User> passengers = new ArrayList<User>();
        passengers.add(user);
        passengers.add(user);
        List<UserRef> passengers1 = new ArrayList<>();
        passengers1.add(new UserRef(user));
        passengers1.add(new UserRef(user));
        Location location = new Location(1L, 10.5, 10.5, "Everywhere at the end of time...");
        Route route = new Route(1L, 10.5, 15, location, location);
        RouteDTO routeDTO = new RouteDTO(route);
        List<RouteDTO> list1389 = new ArrayList<>();
        list1389.add(routeDTO);
        VehicleType vehicleType = new VehicleType(1L, "STANDARD", 100, 100);

        Ride mockRide = new Ride(1L, date, date, RideStatus.STARTED, false, false, false, user, refusal, passengers, route, date, 200, vehicleType);
        FavoriteRide mockFavoriteRide = new FavoriteRide(1L, "Ime", false, false, route, user, "STANDARD");
        FavoriteRideDTO mockFavoriteRideDTO = new FavoriteRideDTO("Ime", list1389, passengers1, "STANDARD", false, false);
        List<FavoriteRide> FRList = new ArrayList<>();
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);

        Vehicle vehicle = new Vehicle(1l, "NS 420 RS", user, vehicleType, 5, false, false, location, "Ford Fiesta");

        Mockito.when(userRepository.findUserByEmail("a")).thenReturn(Optional.of(user));
        Mockito.when(favoriteRideRepository.findAllFavoriteRideByPassengerId(1L)).thenReturn(FRList);

        assertThrows(InvalidArgumentException.class, () -> rideService.postFavorite("a", mockFavoriteRideDTO));
    }

    @Test
    @DisplayName("Should throw aн InvalidArgumentException exception for invalid vehicle type")
    public void PostFavoriteInvalid3()
    {
        Date date = new Date();
        User user = new User(1L, "A", "B", new byte[]{}, "a", "a", "a", "a", true, false, Role.DRIVER);
        Refusal refusal = new Refusal(1L, "No reason.", date, user);
        List<User> passengers = new ArrayList<User>();
        passengers.add(user);
        passengers.add(user);
        List<UserRef> passengers1 = new ArrayList<>();
        passengers1.add(new UserRef(user));
        passengers1.add(new UserRef(user));
        Location location = new Location(1L, 10.5, 10.5, "Everywhere at the end of time...");
        Route route = new Route(1L, 10.5, 15, location, location);
        RouteDTO routeDTO = new RouteDTO(route);
        List<RouteDTO> list1389 = new ArrayList<>();
        list1389.add(routeDTO);
        VehicleType vehicleType = new VehicleType(1L, "STANDARD", 100, 100);

        Ride mockRide = new Ride(1L, date, date, RideStatus.STARTED, false, false, false, user, refusal, passengers, route, date, 200, vehicleType);
        FavoriteRide mockFavoriteRide = new FavoriteRide(1L, "Ime", false, false, route, user, "STANDARD");
        FavoriteRideDTO mockFavoriteRideDTO = new FavoriteRideDTO("Ime", list1389, passengers1, "STANDARD", false, false);
        List<FavoriteRide> FRList = new ArrayList<>();
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);

        Vehicle vehicle = new Vehicle(1l, "NS 420 RS", user, vehicleType, 5, false, false, location, "Ford Fiesta");

        Mockito.when(userRepository.findUserByEmail("a")).thenReturn(Optional.of(user));
        Mockito.when(vehicleTypeRepository.findByTypeName("STANDARD")).thenReturn(Optional.empty());

        assertThrows(InvalidArgumentException.class, () -> rideService.postFavorite("a", mockFavoriteRideDTO));
    }

    @Test
    @DisplayName("Should throw aн InvalidArgumentException exception for invalid locations")
    public void PostFavoriteInvalid4()
    {
        Date date = new Date();
        User user = new User(1L, "A", "B", new byte[]{}, "a", "a", "a", "a", true, false, Role.DRIVER);
        Refusal refusal = new Refusal(1L, "No reason.", date, user);
        List<User> passengers = new ArrayList<User>();
        passengers.add(user);
        passengers.add(user);
        List<UserRef> passengers1 = new ArrayList<>();
        passengers1.add(new UserRef(user));
        passengers1.add(new UserRef(user));
        Location location = new Location(1L, 10.5, 10.5, "Everywhere at the end of time...");
        Route route = new Route(1L, 10.5, 15, location, location);
        RouteDTO routeDTO = new RouteDTO(route);
        List<RouteDTO> list1389 = new ArrayList<>();
        list1389.add(routeDTO);
        VehicleType vehicleType = new VehicleType(1L, "STANDARD", 100, 100);

        Ride mockRide = new Ride(1L, date, date, RideStatus.STARTED, false, false, false, user, refusal, passengers, route, date, 200, vehicleType);
        FavoriteRide mockFavoriteRide = new FavoriteRide(1L, "Ime", false, false, route, user, "STANDARD");
        FavoriteRideDTO mockFavoriteRideDTO = new FavoriteRideDTO("Ime", null, passengers1, "STANDARD", false, false);
        List<FavoriteRide> FRList = new ArrayList<>();
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);
        FRList.add(mockFavoriteRide);

        Vehicle vehicle = new Vehicle(1l, "NS 420 RS", user, vehicleType, 5, false, false, location, "Ford Fiesta");

        Mockito.when(userRepository.findUserByEmail("a")).thenReturn(Optional.of(user));
        Mockito.when(favoriteRideRepository.findAllFavoriteRideByPassengerId(1L)).thenReturn(FRList);
        Mockito.when(vehicleTypeRepository.findByTypeName("STANDARD")).thenReturn(Optional.of(vehicleType));

        assertThrows(InvalidArgumentException.class, () -> rideService.postFavorite("a", mockFavoriteRideDTO));
    }
}
