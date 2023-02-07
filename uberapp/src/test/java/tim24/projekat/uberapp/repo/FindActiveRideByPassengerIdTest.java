package tim24.projekat.uberapp.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import tim24.projekat.uberapp.model.Ride;
import tim24.projekat.uberapp.model.User;

import java.util.Optional;

import static org.testng.Assert.*;


@DataJpaTest
//@ActiveProfiles("test")
//@TestPropertySource(locations="classpath:application.properties")
public class FindActiveRideByPassengerIdTest {

    @Autowired
    private tim24.projekat.uberapp.repo.RideRepository rideRepository;

    @Autowired
    private tim24.projekat.uberapp.repo.UserRepository userRepository;

    @Test
    public void shouldFindActivePendingRideForPassenger() {
        User user1 = new User();
        user1.setName("mirko");
        userRepository.save(user1);

        User passenger = userRepository.findUserById(3L).orElse(null);
        System.out.println(" xdddd "+passenger.getName());
        assertTrue(passenger.getName().equals("piskula"));
        assertNotNull(passenger);

        //Optional<Ride> activeRide = rideRepository.findActiveRideByPassengerId(passenger.getId());
        //assertFalse(activeRide.isEmpty());
    }

    @Test
    public void shouldFindActiveStartedRideForPassenger() {
        User passenger = userRepository.findUserById(1L).orElse(null);
        assertNotNull(passenger);

        Optional<Ride> activeRide = rideRepository.findActiveRideByPassengerId(passenger.getId());
        assertFalse(activeRide.isEmpty());
    }

    @Test
    public void shouldFindActiveAcceptedRideForPassenger() {
        User passenger = userRepository.findUserById(1L).orElse(null);
        assertNotNull(passenger);

        Optional<Ride> activeRide = rideRepository.findActiveRideByPassengerId(passenger.getId());
        assertFalse(activeRide.isEmpty());
    }

    @Test
    public void shouldNotFindActiveRideForPassengerIfNotExists() {
        User passenger = userRepository.findUserById(1L).orElse(null);
        assertNotNull(passenger);

        Optional<Ride> activeRide = rideRepository.findActiveRideByPassengerId(passenger.getId());
        assertTrue(activeRide.isEmpty());
    }

    @Test
    public void shouldNotFindActiveRideForPassengerIfRideFinished() {
        User passenger = userRepository.findUserById(1L).orElse(null);
        assertNotNull(passenger);

        Optional<Ride> activeRide = rideRepository.findActiveRideByPassengerId(passenger.getId());
        assertTrue(activeRide.isEmpty());
    }

    @Test
    public void shouldNotFindActiveRideForPassengerIfPassengerDoesntExist() {

        Optional<Ride> activeRide = rideRepository.findActiveRideByPassengerId(1L);
        assertTrue(activeRide.isEmpty());
    }

}