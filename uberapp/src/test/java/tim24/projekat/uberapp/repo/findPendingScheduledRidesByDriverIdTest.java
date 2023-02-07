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

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;


@DataJpaTest
//@Sql("classpath:testdata.sql")
@ActiveProfiles("test")
@TestPropertySource(locations="classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class findPendingScheduledRidesByDriverIdTest {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindScheduledRidesForDriverInOrder() {

        User user = new User();
        Date now = new Date();

        Ride ride1 = new Ride();
        ride1.setDriver(user);
        Date future1 = Date.from(now.toInstant().plus(Duration.of(45, ChronoUnit.MINUTES)));
        ride1.setScheduledTime(future1);

        Ride ride2 = new Ride();
        ride2.setDriver(user);
        Date future2 = Date.from(now.toInstant().plus(Duration.of(30, ChronoUnit.MINUTES)));
        ride2.setScheduledTime(future2);

        userRepository.save(user);
        rideRepository.save(ride1);
        rideRepository.save(ride2);

        User driver = userRepository.findUserById(1L).orElse(null);
        assertNotNull(driver);

        Optional<List<Ride>> rides = rideRepository.findPendingScheduledRidesByDriverId(driver.getId());
        assertNotNull(rides.get().get(0));
        assertNotNull(rides.get().get(1));
        assertTrue(rides.get().get(0).getScheduledTime().equals(future2)); //prvo smo stavili kasniju pa raniju ali treba da dodje obrnuto
        assertTrue(rides.get().get(1).getScheduledTime().equals(future1));
    }

    @Test
    public void shouldNotFindScheduledRidesIfDriverDoesNotExist() {

        User user = new User();
        Date now = new Date();

        Ride ride1 = new Ride();
        ride1.setDriver(user);
        Date future1 = Date.from(now.toInstant().plus(Duration.of(45, ChronoUnit.MINUTES)));
        ride1.setScheduledTime(future1);

        userRepository.save(user);
        rideRepository.save(ride1);

        User driver = userRepository.findUserById(2L).orElse(null);
        assertNull(driver);

        Optional<List<Ride>> rides = rideRepository.findPendingScheduledRidesByDriverId(2L);
        assertTrue(rides.get().isEmpty());

    }

    @Test
    public void shouldNotFindScheduledRidesIfTheyAreInPast() {

        User user = new User();
        Date now = new Date();

        Ride ride1 = new Ride();
        ride1.setDriver(user);
        Date future1 = Date.from(now.toInstant().minus(Duration.of(45, ChronoUnit.MINUTES)));
        ride1.setScheduledTime(future1);

        userRepository.save(user);
        rideRepository.save(ride1);

        User driver = userRepository.findUserById(user.getId()).orElse(null);
        assertNotNull(driver);

        Optional<List<Ride>> rides = rideRepository.findPendingScheduledRidesByDriverId(user.getId());
        assertTrue(rides.get().isEmpty());
    }

    @Test
    public void shouldNotFindScheduledRidesIfTheyAreInPresent() {

        User user = new User();
        Date now = new Date();

        Ride ride1 = new Ride();
        ride1.setDriver(user);
        ride1.setScheduledTime(now);

        userRepository.save(user);
        rideRepository.save(ride1);

        User driver = userRepository.findUserById(user.getId()).orElse(null);
        assertNotNull(driver);

        Optional<List<Ride>> rides = rideRepository.findPendingScheduledRidesByDriverId(user.getId());
        assertTrue(rides.get().isEmpty());
    }

    @Test
    public void shouldNotFindScheduledRidesIfThereAreNoRides() {

        User user = new User();
        Date now = new Date();

        userRepository.save(user);

        User driver = userRepository.findUserById(user.getId()).orElse(null);
        assertNotNull(driver);

        Optional<List<Ride>> rides = rideRepository.findPendingScheduledRidesByDriverId(user.getId());
        assertTrue(rides.get().isEmpty());
    }

    @Test
    public void shouldOnlyReturnRideWithScheduledTime() {

        User user = new User();
        Date now = new Date();

        Ride ride1 = new Ride();
        ride1.setDriver(user);
        ride1.setScheduledTime(now);

        Ride ride2 = new Ride();
        ride2.setDriver(user);
        Date future = Date.from(now.toInstant().plus(Duration.of(45, ChronoUnit.MINUTES)));
        ride2.setScheduledTime(future);

        userRepository.save(user);
        rideRepository.save(ride1);
        rideRepository.save(ride2);

        User driver = userRepository.findUserById(user.getId()).orElse(null);
        assertNotNull(driver);

        Optional<List<Ride>> rides = rideRepository.findPendingScheduledRidesByDriverId(user.getId());
        assertEquals(rides.get().size(),1);
        assertEquals(rides.get().get(0).getScheduledTime(),future);
    }

    @Test
    public void shouldOnlyReturnRidesForGivenDriver() {

        User user1 = new User();
        User user2 = new User();
        Date now = new Date();
        Date future = Date.from(now.toInstant().plus(Duration.of(55, ChronoUnit.MINUTES)));

        Ride ride1 = new Ride();
        ride1.setDriver(user1);
        ride1.setScheduledTime(future);

        Ride ride2 = new Ride();
        ride2.setDriver(user2);
        ride2.setScheduledTime(future);

        userRepository.save(user1);
        userRepository.save(user2);
        rideRepository.save(ride1);
        rideRepository.save(ride2);

        User driver1 = userRepository.findUserById(user1.getId()).orElse(null);
        assertNotNull(driver1);


        Optional<List<Ride>> rides = rideRepository.findPendingScheduledRidesByDriverId(user1.getId());
        assertEquals(rides.get().size(),1);
    }

}