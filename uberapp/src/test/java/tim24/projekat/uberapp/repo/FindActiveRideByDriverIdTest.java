package tim24.projekat.uberapp.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
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
public class FindActiveRideByDriverIdTest {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindActivePendingRideForDriver() {

        User user = new User();
        Ride ride = new Ride();
        ride.setDriver(user);
        ride.setStatus(RideStatus.PENDING);

        userRepository.save(user);
        rideRepository.save(ride);

        User driver = userRepository.findUserById(1L).orElse(null);
        assertNotNull(driver);

        Optional<Ride> activeRide = rideRepository.findActiveRideByDriverId(driver.getId());
        assertTrue(activeRide.isPresent());
    }

    @Test
    public void shouldFindActiveStartedRideForDriver() {

        User user = new User();
        Ride ride = new Ride();
        ride.setDriver(user);
        ride.setStatus(RideStatus.STARTED);

        userRepository.save(user);
        rideRepository.save(ride);

        User driver = userRepository.findUserById(1L).orElse(null);
        assertNotNull(driver);

        Optional<Ride> activeRide = rideRepository.findActiveRideByDriverId(driver.getId());
        assertTrue(activeRide.isPresent());
    }

    @Test
    public void shouldFindActiveAcceptedRideForDriver() {

        User user = new User();
        Ride ride = new Ride();
        ride.setDriver(user);
        ride.setStatus(RideStatus.ACCEPTED);

        userRepository.save(user);
        rideRepository.save(ride);

        User driver = userRepository.findUserById(1L).orElse(null);
        assertNotNull(driver);

        Optional<Ride> activeRide = rideRepository.findActiveRideByDriverId(driver.getId());
        assertTrue(activeRide.isPresent());
    }

    @Test
    public void shouldNotFindActiveRideIfOnlyFinished() {

        User user = new User();
        Ride ride = new Ride();
        ride.setDriver(user);
        ride.setStatus(RideStatus.FINISHED);

        userRepository.save(user);
        rideRepository.save(ride);

        User driver = userRepository.findUserById(1L).orElse(null);
        assertNotNull(driver);

        Optional<Ride> activeRide = rideRepository.findActiveRideByDriverId(driver.getId());
        assertFalse(activeRide.isPresent());
    }

    @Test
    public void shouldNotFindActiveRideIfDriverNotExist() {

        User user = new User();
        Ride ride = new Ride();
        ride.setDriver(user);
        ride.setStatus(RideStatus.PENDING);

        userRepository.save(user);
        rideRepository.save(ride);

        User driver = userRepository.findUserById(2L).orElse(null);
        assertNull(driver);

        Optional<Ride> activeRide = rideRepository.findActiveRideByDriverId(2L);
        assertFalse(activeRide.isPresent());
    }

}