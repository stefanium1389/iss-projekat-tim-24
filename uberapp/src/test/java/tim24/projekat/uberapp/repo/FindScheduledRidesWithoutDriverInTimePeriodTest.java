package tim24.projekat.uberapp.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import tim24.projekat.uberapp.model.Ride;
import tim24.projekat.uberapp.model.RideStatus;
import tim24.projekat.uberapp.model.User;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.testng.Assert.*;


@DataJpaTest
//@Sql("classpath:testdata.sql")
@ActiveProfiles("test")
@TestPropertySource(locations="classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FindScheduledRidesWithoutDriverInTimePeriodTest {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindScheduledRidesThatIsInTimeInterval() {


        Date now = new Date();
        Date middle = Date.from(now.toInstant().plus(Duration.of(45, ChronoUnit.MINUTES)));
        Date end = Date.from(now.toInstant().plus(Duration.of(90, ChronoUnit.MINUTES)));

        Ride ride1 = new Ride();
        ride1.setScheduledTime(middle);
        ride1.setStatus(RideStatus.PENDING);
        rideRepository.save(ride1);

        List<Ride> rides = rideRepository.findScheduledRidesWithoutDriverInTimePeriod(now,end);
        assertFalse(rides.isEmpty());
        assertEquals(rides.size(),1);

    }

    @Test
    public void shouldNotFindRideIfScheduledTimeIsBeforeStartDate() {

        Date now = new Date();
        Date start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date end = Date.from(now.toInstant().plus(Duration.of(80, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(20, ChronoUnit.MINUTES)));

        Ride ride1 = new Ride();
        ride1.setScheduledTime(test_time);
        ride1.setStatus(RideStatus.PENDING);
        rideRepository.save(ride1);

        List<Ride> rides = rideRepository.findScheduledRidesWithoutDriverInTimePeriod(start,end);
        assertTrue(rides.isEmpty());

    }

    @Test
    public void shouldFindRideIfScheduledTimeIsStartDate() {

        Date now = new Date();
        Date start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date end = Date.from(now.toInstant().plus(Duration.of(80, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));

        Ride ride1 = new Ride();
        ride1.setScheduledTime(test_time);
        ride1.setStatus(RideStatus.PENDING);
        rideRepository.save(ride1);

        List<Ride> rides = rideRepository.findScheduledRidesWithoutDriverInTimePeriod(start,end);
        assertFalse(rides.isEmpty());
    }

    @Test
    public void shouldFindRideIfScheduledTimeIsBetweenStartAndEndDate() {

        Date now = new Date();
        Date start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date end = Date.from(now.toInstant().plus(Duration.of(80, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));

        Ride ride1 = new Ride();
        ride1.setScheduledTime(test_time);
        ride1.setStatus(RideStatus.PENDING);
        rideRepository.save(ride1);

        List<Ride> rides = rideRepository.findScheduledRidesWithoutDriverInTimePeriod(start,end);
        assertFalse(rides.isEmpty());
    }

    @Test
    public void shouldFindRideIfScheduledTimeStartsAtEndDate() {

        Date now = new Date();
        Date start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date end = Date.from(now.toInstant().plus(Duration.of(80, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(80, ChronoUnit.MINUTES)));

        Ride ride1 = new Ride();
        ride1.setScheduledTime(test_time);
        ride1.setStatus(RideStatus.PENDING);
        rideRepository.save(ride1);

        List<Ride> rides = rideRepository.findScheduledRidesWithoutDriverInTimePeriod(start,end);
        assertFalse(rides.isEmpty());
    }

    @Test
    public void shouldNotFindRideIfScheduledTimeIsAfterEndDate() {

        Date now = new Date();
        Date start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date end = Date.from(now.toInstant().plus(Duration.of(80, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(90, ChronoUnit.MINUTES)));

        Ride ride1 = new Ride();
        ride1.setScheduledTime(test_time);
        ride1.setStatus(RideStatus.PENDING);
        rideRepository.save(ride1);

        List<Ride> rides = rideRepository.findScheduledRidesWithoutDriverInTimePeriod(start,end);
        assertTrue(rides.isEmpty());
    }

    @Test
    public void shouldOnlyFindRidesThatAreInTimeInterval() {

        Date now = new Date();
        Date start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date end = Date.from(now.toInstant().plus(Duration.of(80, ChronoUnit.MINUTES)));
        Date test_time1 = Date.from(now.toInstant().plus(Duration.of(20, ChronoUnit.MINUTES)));
        Date test_time2 = Date.from(now.toInstant().plus(Duration.of(50, ChronoUnit.MINUTES)));
        Date test_time3 = Date.from(now.toInstant().plus(Duration.of(90, ChronoUnit.MINUTES)));

        Ride ride1 = new Ride();
        ride1.setScheduledTime(test_time1);
        ride1.setStatus(RideStatus.PENDING);
        rideRepository.save(ride1);

        Ride ride2 = new Ride();
        ride2.setScheduledTime(test_time2);
        ride2.setStatus(RideStatus.PENDING);
        rideRepository.save(ride2);

        Ride ride3 = new Ride();
        ride3.setScheduledTime(test_time3);
        ride3.setStatus(RideStatus.PENDING);
        rideRepository.save(ride3);

        List<Ride> rides = rideRepository.findScheduledRidesWithoutDriverInTimePeriod(start,end);
        assertFalse(rides.isEmpty());
        assertEquals(rides.size(),1);
    }

    @Test
    public void shouldNotFindIfRideHasDriver() {

        User driver = new User();
        userRepository.save(driver);

        Date now = new Date();
        Date start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date end = Date.from(now.toInstant().plus(Duration.of(80, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(50, ChronoUnit.MINUTES)));

        Ride ride1 = new Ride();
        ride1.setScheduledTime(test_time);
        ride1.setStatus(RideStatus.PENDING);
        ride1.setDriver(driver);
        rideRepository.save(ride1);

        List<Ride> rides = rideRepository.findScheduledRidesWithoutDriverInTimePeriod(start,end);
        assertTrue(rides.isEmpty());
    }

    @Test
    public void shouldOnlyFindRidesWithoutDriver() {

        User driver = new User();
        userRepository.save(driver);

        Date now = new Date();
        Date start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date end = Date.from(now.toInstant().plus(Duration.of(80, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(50, ChronoUnit.MINUTES)));

        Ride ride1 = new Ride();
        ride1.setScheduledTime(test_time);
        ride1.setStatus(RideStatus.PENDING);
        ride1.setDriver(driver);
        rideRepository.save(ride1);

        Ride ride2 = new Ride();
        ride2.setScheduledTime(test_time);
        ride2.setStatus(RideStatus.PENDING);
        rideRepository.save(ride2);

        List<Ride> rides = rideRepository.findScheduledRidesWithoutDriverInTimePeriod(start,end);
        assertFalse(rides.isEmpty());
        assertEquals(rides.size(),1);
    }

    @Test
    public void shouldOnlyFindScheduledRides() {

        Date now = new Date();
        Date start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date end = Date.from(now.toInstant().plus(Duration.of(80, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(50, ChronoUnit.MINUTES)));

        Ride ride1 = new Ride();
        ride1.setStatus(RideStatus.PENDING);
        rideRepository.save(ride1);

        Ride ride2 = new Ride();
        ride2.setScheduledTime(test_time);
        ride2.setStatus(RideStatus.PENDING);
        rideRepository.save(ride2);

        List<Ride> rides = rideRepository.findScheduledRidesWithoutDriverInTimePeriod(start,end);
        assertFalse(rides.isEmpty());
        assertEquals(rides.size(),1);
    }

    @Test
    public void shouldOnlyFindPendingRides() {

        Date now = new Date();
        Date start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date end = Date.from(now.toInstant().plus(Duration.of(80, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(50, ChronoUnit.MINUTES)));

        Ride ride1 = new Ride();
        ride1.setScheduledTime(test_time);
        ride1.setStatus(RideStatus.PENDING);
        rideRepository.save(ride1);

        Ride ride2 = new Ride();
        ride2.setScheduledTime(test_time);
        ride2.setStatus(RideStatus.CANCELED);
        rideRepository.save(ride2);

        List<Ride> rides = rideRepository.findScheduledRidesWithoutDriverInTimePeriod(start,end);
        assertFalse(rides.isEmpty());
        assertEquals(rides.size(),1);
    }
}