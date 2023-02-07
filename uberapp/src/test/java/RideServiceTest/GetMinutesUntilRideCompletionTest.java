package RideServiceTest;

import jakarta.persistence.Access;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import tim24.projekat.uberapp.exception.InvalidTimeException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.model.*;
import tim24.projekat.uberapp.service.RideService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class GetMinutesUntilRideCompletionTest
{
    @Autowired
    @InjectMocks
    private RideService rideService;

    @Test
    @DisplayName("Should return estimated time left")
    public void testEstimateValid()
    {
        Calendar date = Calendar.getInstance();
        long timeInMilisecs = date.getTimeInMillis();
        Date start = new Date(timeInMilisecs - (10 * 60 * 1000));
        Date end = new Date(2023, 4, 1, 12, 0, 0);
        User user = new User();
        Refusal refusal = new Refusal(1L, "No reason.", start, user);
        List<User> passengers = new ArrayList<User>();
        passengers.add(user);
        passengers.add(user);
        Location location = new Location(1L, 10.5, 10.5, "Everywhere at the end of time...");
        Route route = new Route(1L, 10.5, 15, location, location);
        VehicleType vehicleType = new VehicleType(1L, "STANDARD", 100, 100);

        Ride mockRide = new Ride(1L, start, end, RideStatus.STARTED, false, false, false, user, refusal, passengers, route, new Date(), 200, vehicleType);

        int actualMinutes = rideService.getMinutesUntilRideCompletion(mockRide);

        assertEquals(actualMinutes, 5);
    }

    @Test
    @DisplayName("Should return getEstimatedTime() of the ride")
    public void testEstimateInvalid()
    {
        Calendar date = Calendar.getInstance();
        long timeInMilisecs = date.getTimeInMillis();
        Date start = new Date(timeInMilisecs - (10 * 60 * 1000));
        Date end = new Date(2023, 4, 1, 12, 0, 0);
        User user = new User();
        Refusal refusal = new Refusal(1L, "No reason.", start, user);
        List<User> passengers = new ArrayList<User>();
        passengers.add(user);
        passengers.add(user);
        Location location = new Location(1L, 10.5, 10.5, "Everywhere at the end of time...");
        Route route = new Route(1L, 10.5, 5, location, location);
        VehicleType vehicleType = new VehicleType(1L, "STANDARD", 100, 100);

        Ride mockRide = new Ride(1L, start, end, RideStatus.STARTED, false, false, false, user, refusal, passengers, route, new Date(), 200, vehicleType);

        int actualMinutes = rideService.getMinutesUntilRideCompletion(mockRide);

        assertEquals(actualMinutes, 5);
    }

    @Test
    @DisplayName("Should throw an InvalidTimeException exception")
    public void testEstimateTooEarly()
    {
        Calendar date = Calendar.getInstance();
        long timeInMilisecs = date.getTimeInMillis();
        Date start = new Date(2023, 4, 1, 12, 0, 0);
        Date end = new Date(2023, 4, 1, 12, 0, 0);
        User user = new User();
        Refusal refusal = new Refusal(1L, "No reason.", start, user);
        List<User> passengers = new ArrayList<User>();
        passengers.add(user);
        passengers.add(user);
        Location location = new Location(1L, 10.5, 10.5, "Everywhere at the end of time...");
        Route route = new Route(1L, 10.5, 5, location, location);
        VehicleType vehicleType = new VehicleType(1L, "STANDARD", 100, 100);

        Ride mockRide = new Ride(1L, start, end, RideStatus.STARTED, false, false, false, user, refusal, passengers, route, new Date(), 200, vehicleType);

        assertThrows(InvalidTimeException.class, () -> rideService.getMinutesUntilRideCompletion(mockRide));
    }
}
