package tim24.projekat.uberapp.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import tim24.projekat.uberapp.model.Ride;
import tim24.projekat.uberapp.model.RideStatus;
import tim24.projekat.uberapp.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;


@DataJpaTest
//@Sql("classpath:testdata.sql")
@ActiveProfiles("test")
@TestPropertySource(locations="classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FindActiveRideByPassengerIdTest {

    @Autowired
    private tim24.projekat.uberapp.repo.RideRepository rideRepository;

    @Autowired
    private tim24.projekat.uberapp.repo.UserRepository userRepository;

    @Test
    public void shouldFindActivePendingRideForPassenger() {

        User user = new User();
        List<User> list = new ArrayList<>();
        list.add(user);
        Ride ride = new Ride();
        ride.setPassengers(list);
        ride.setStatus(RideStatus.PENDING);

        userRepository.save(user);
        rideRepository.save(ride);

        User passenger = userRepository.findUserById(1L).orElse(null);
        assertNotNull(passenger);

        Optional<Ride> activeRide = rideRepository.findActiveRideByPassengerId(passenger.getId());
        assertFalse(activeRide.isEmpty());
    }

    @Test
    public void shouldFindActiveStartedRideForPassenger() {
        User user = new User();
        List<User> list = new ArrayList<>();
        list.add(user);
        Ride ride = new Ride();
        ride.setPassengers(list);
        ride.setStatus(RideStatus.STARTED);

        userRepository.save(user);
        rideRepository.save(ride);

        User passenger = userRepository.findUserById(1L).orElse(null);
        assertNotNull(passenger);

        Optional<Ride> activeRide = rideRepository.findActiveRideByPassengerId(passenger.getId());
        assertFalse(activeRide.isEmpty());
    }

    @Test
    public void shouldFindActiveAcceptedRideForPassenger() {
        User user = new User();
        List<User> list = new ArrayList<>();
        list.add(user);
        Ride ride = new Ride();
        ride.setPassengers(list);
        ride.setStatus(RideStatus.ACCEPTED);

        userRepository.save(user);
        rideRepository.save(ride);

        User passenger = userRepository.findUserById(1L).orElse(null);
        assertNotNull(passenger);

        Optional<Ride> activeRide = rideRepository.findActiveRideByPassengerId(passenger.getId());
        assertFalse(activeRide.isEmpty());
    }

    @Test
    public void shouldNotFindActiveRideForPassengerIfRideFinished() {
        User user = new User();
        List<User> list = new ArrayList<>();
        list.add(user);
        Ride ride = new Ride();
        ride.setPassengers(list);
        ride.setStatus(RideStatus.FINISHED);

        userRepository.save(user);
        rideRepository.save(ride);

        User passenger = userRepository.findUserById(1L).orElse(null);
        assertNotNull(passenger);

        Optional<Ride> activeRide = rideRepository.findActiveRideByPassengerId(passenger.getId());
        assertTrue(activeRide.isEmpty());
    }

    @Test
    public void shouldNotFindActiveRideForPassengerIfPassengerDoesntExist() {

        User user = new User();
        List<User> list = new ArrayList<>();
        list.add(user);
        Ride ride = new Ride();
        ride.setPassengers(list);
        ride.setStatus(RideStatus.ACCEPTED);

        userRepository.save(user);
        rideRepository.save(ride);

        User passenger = userRepository.findUserById(2L).orElse(null);
        assertNull(passenger);

        Optional<Ride> activeRide = rideRepository.findActiveRideByPassengerId(2L);
        assertTrue(activeRide.isEmpty());
    }

}