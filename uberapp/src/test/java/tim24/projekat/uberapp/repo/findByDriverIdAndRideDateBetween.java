package tim24.projekat.uberapp.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import tim24.projekat.uberapp.model.Ride;
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
public class findByDriverIdAndRideDateBetween {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindRidesForDriverIfRidesAreInIntervalAndBelongToDriver() {

        User user = new User();
        Date now = new Date();
        Date middle = Date.from(now.toInstant().plus(Duration.of(45, ChronoUnit.MINUTES)));
        Date end = Date.from(now.toInstant().plus(Duration.of(90, ChronoUnit.MINUTES)));
        Pageable pageable = PageRequest.of(0, 100);

        Ride ride1 = new Ride();
        ride1.setDriver(user);

        ride1.setStartTime(middle);

        userRepository.save(user);
        rideRepository.save(ride1);

        User driver = userRepository.findUserById(1L).orElse(null);
        assertNotNull(driver);

        Page<Ride> rides = rideRepository.findByDriverIdAndRideDateBetween(driver.getId(),now,end,pageable);
        assertFalse(rides.isEmpty());
        assertEquals(rides.getNumberOfElements(),1);

    }

    @Test
    public void shouldNotFindRideIfRideStartsBeforeStartDate() {

        User user = new User();
        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(20, ChronoUnit.MINUTES)));

        Pageable pageable = PageRequest.of(0, 100);

        Ride ride1 = new Ride();
        ride1.setDriver(user);

        ride1.setStartTime(test_time);

        userRepository.save(user);
        rideRepository.save(ride1);

        User driver = userRepository.findUserById(1L).orElse(null);
        assertNotNull(driver);

        Page<Ride> rides = rideRepository.findByDriverIdAndRideDateBetween(driver.getId(),interval_start,interval_end,pageable);
        assertTrue(rides.isEmpty());

    }

    @Test
    public void shouldFindRideIfRideStartsAtStartDate() {

        User user = new User();
        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));

        Pageable pageable = PageRequest.of(0, 100);

        Ride ride1 = new Ride();
        ride1.setDriver(user);

        ride1.setStartTime(test_time);

        userRepository.save(user);
        rideRepository.save(ride1);

        User driver = userRepository.findUserById(1L).orElse(null);
        assertNotNull(driver);

        Page<Ride> rides = rideRepository.findByDriverIdAndRideDateBetween(driver.getId(),interval_start,interval_end,pageable);
        assertFalse(rides.isEmpty());
    }

    @Test
    public void shouldFindRideIfRideStartsBetweenStartAndEndDate() {

        User user = new User();
        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(50, ChronoUnit.MINUTES)));

        Pageable pageable = PageRequest.of(0, 100);

        Ride ride1 = new Ride();
        ride1.setDriver(user);

        ride1.setStartTime(test_time);

        userRepository.save(user);
        rideRepository.save(ride1);

        User driver = userRepository.findUserById(1L).orElse(null);
        assertNotNull(driver);

        Page<Ride> rides = rideRepository.findByDriverIdAndRideDateBetween(driver.getId(),interval_start,interval_end,pageable);
        assertFalse(rides.isEmpty());
    }

    @Test
    public void shouldFindRideIfRideStartsAtEndDate() {

        User user = new User();
        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));

        Pageable pageable = PageRequest.of(0, 100);

        Ride ride1 = new Ride();
        ride1.setDriver(user);

        ride1.setStartTime(test_time);

        userRepository.save(user);
        rideRepository.save(ride1);

        User driver = userRepository.findUserById(1L).orElse(null);
        assertNotNull(driver);

        Page<Ride> rides = rideRepository.findByDriverIdAndRideDateBetween(driver.getId(),interval_start,interval_end,pageable);
        assertFalse(rides.isEmpty());
    }

    @Test
    public void shouldNotFindRideIfRideStartsAfterEndDate() {

        User user = new User();
        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(70, ChronoUnit.MINUTES)));

        Pageable pageable = PageRequest.of(0, 100);

        Ride ride1 = new Ride();
        ride1.setDriver(user);

        ride1.setStartTime(test_time);

        userRepository.save(user);
        rideRepository.save(ride1);

        User driver = userRepository.findUserById(1L).orElse(null);
        assertNotNull(driver);

        Page<Ride> rides = rideRepository.findByDriverIdAndRideDateBetween(driver.getId(),interval_start,interval_end,pageable);
        assertTrue(rides.isEmpty());
    }

    @Test
    public void shouldOnlyFindRidesThatAreInTimeInterval() {

        User user = new User();
        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));
        Date time1 = Date.from(now.toInstant().plus(Duration.of(20, ChronoUnit.MINUTES)));
        Date time2 = Date.from(now.toInstant().plus(Duration.of(50, ChronoUnit.MINUTES)));
        Date time3 = Date.from(now.toInstant().plus(Duration.of(90, ChronoUnit.MINUTES)));

        Pageable pageable = PageRequest.of(0, 100);

        Ride ride1 = new Ride();
        ride1.setDriver(user);

        ride1.setStartTime(time1);

        Ride ride2 = new Ride();
        ride2.setDriver(user);

        ride2.setStartTime(time2);

        Ride ride3 = new Ride();
        ride3.setDriver(user);

        ride3.setStartTime(time3);

        userRepository.save(user);
        rideRepository.save(ride1);
        rideRepository.save(ride2);
        rideRepository.save(ride3);

        User driver = userRepository.findUserById(1L).orElse(null);
        assertNotNull(driver);

        Page<Ride> rides = rideRepository.findByDriverIdAndRideDateBetween(driver.getId(),interval_start,interval_end,pageable);
        assertFalse(rides.isEmpty());
        assertEquals(rides.getNumberOfElements(),1);
    }

    @Test
    public void shouldOnlyFindRidesThatAreInTimeIntervalAndBelongToDriver() {

        User user = new User();
        User user2 = new User();
        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));
        Date time1 = Date.from(now.toInstant().plus(Duration.of(20, ChronoUnit.MINUTES)));
        Date time2 = Date.from(now.toInstant().plus(Duration.of(50, ChronoUnit.MINUTES)));
        Date time3 = Date.from(now.toInstant().plus(Duration.of(90, ChronoUnit.MINUTES)));

        Pageable pageable = PageRequest.of(0, 100);

        Ride ride1 = new Ride();
        ride1.setDriver(user);
        ride1.setStartTime(time1);

        Ride ride2 = new Ride();
        ride2.setDriver(user);
        ride2.setStartTime(time2);

        Ride ride2driver2 = new Ride();
        ride2driver2.setDriver(user2);
        ride2driver2.setStartTime(time2);

        Ride ride3 = new Ride();
        ride3.setDriver(user);
        ride3.setStartTime(time3);

        userRepository.save(user);
        userRepository.save(user2);
        rideRepository.save(ride1);
        rideRepository.save(ride2);
        rideRepository.save(ride2driver2);
        rideRepository.save(ride3);

        User driver = userRepository.findUserById(1L).orElse(null);
        assertNotNull(driver);

        Page<Ride> rides = rideRepository.findByDriverIdAndRideDateBetween(driver.getId(),interval_start,interval_end,pageable);
        assertFalse(rides.isEmpty());
        assertEquals(rides.getNumberOfElements(),1);
    }

}