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
import java.util.Optional;

import static org.testng.Assert.*;


@DataJpaTest
//@Sql("classpath:testdata.sql")
@ActiveProfiles("test")
@TestPropertySource(locations="classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FindRidesWithStatusBetweenDatesTest {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindPendingRideBetweenDates() {

        Date now = new Date();
        Ride ride1 = new Ride();
        Date start = Date.from(now.toInstant().plus(Duration.of(30, ChronoUnit.MINUTES)));
        Date end = Date.from(now.toInstant().plus(Duration.of(90, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(70, ChronoUnit.MINUTES)));

        RideStatus status = RideStatus.PENDING;
        ride1.setEndTime(test_time);
        ride1.setStatus(status);

        rideRepository.save(ride1);

        List<Ride> rides = rideRepository.findRidesWithStatusBetweenDates(status,start,end);
        assertFalse(rides.isEmpty());
        assertEquals(rides.get(0).getStatus(),status);
    }

    @Test
    public void shouldFindAcceptedRideBetweenDates() {

        Date now = new Date();
        Ride ride1 = new Ride();
        Date start = Date.from(now.toInstant().plus(Duration.of(30, ChronoUnit.MINUTES)));
        Date end = Date.from(now.toInstant().plus(Duration.of(90, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(70, ChronoUnit.MINUTES)));

        RideStatus status = RideStatus.ACCEPTED;
        ride1.setEndTime(test_time);
        ride1.setStatus(status);

        rideRepository.save(ride1);

        List<Ride> rides = rideRepository.findRidesWithStatusBetweenDates(status,start,end);
        assertFalse(rides.isEmpty());
        assertEquals(rides.get(0).getStatus(),status);
    }

    @Test
    public void shouldFindCanceledRideBetweenDates() {

        Date now = new Date();
        Ride ride1 = new Ride();
        Date start = Date.from(now.toInstant().plus(Duration.of(30, ChronoUnit.MINUTES)));
        Date end = Date.from(now.toInstant().plus(Duration.of(90, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(70, ChronoUnit.MINUTES)));

        RideStatus status = RideStatus.CANCELED;
        ride1.setEndTime(test_time);
        ride1.setStatus(status);

        rideRepository.save(ride1);

        List<Ride> rides = rideRepository.findRidesWithStatusBetweenDates(status,start,end);
        assertFalse(rides.isEmpty());
        assertEquals(rides.get(0).getStatus(),status);
    }

    @Test
    public void shouldFindStartedRideBetweenDates() {

        Date now = new Date();
        Ride ride1 = new Ride();
        Date start = Date.from(now.toInstant().plus(Duration.of(30, ChronoUnit.MINUTES)));
        Date end = Date.from(now.toInstant().plus(Duration.of(90, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(70, ChronoUnit.MINUTES)));

        RideStatus status = RideStatus.STARTED;
        ride1.setEndTime(test_time);
        ride1.setStatus(status);

        rideRepository.save(ride1);

        List<Ride> rides = rideRepository.findRidesWithStatusBetweenDates(status,start,end);
        assertFalse(rides.isEmpty());
        assertEquals(rides.get(0).getStatus(),status);
    }

    @Test
    public void shouldFindFinishedRideBetweenDates() {

        Date now = new Date();
        Ride ride1 = new Ride();
        Date start = Date.from(now.toInstant().plus(Duration.of(30, ChronoUnit.MINUTES)));
        Date end = Date.from(now.toInstant().plus(Duration.of(90, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(70, ChronoUnit.MINUTES)));

        RideStatus status = RideStatus.FINISHED;
        ride1.setEndTime(test_time);
        ride1.setStatus(status);

        rideRepository.save(ride1);

        List<Ride> rides = rideRepository.findRidesWithStatusBetweenDates(status,start,end);
        assertFalse(rides.isEmpty());
        assertEquals(rides.get(0).getStatus(),status);
    }

    @Test
    public void shouldFindRejectedRideBetweenDates() {

        Date now = new Date();
        Ride ride1 = new Ride();
        Date start = Date.from(now.toInstant().plus(Duration.of(30, ChronoUnit.MINUTES)));
        Date end = Date.from(now.toInstant().plus(Duration.of(90, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(70, ChronoUnit.MINUTES)));

        RideStatus status = RideStatus.REJECTED;
        ride1.setEndTime(test_time);
        ride1.setStatus(status);

        rideRepository.save(ride1);

        List<Ride> rides = rideRepository.findRidesWithStatusBetweenDates(status,start,end);
        assertFalse(rides.isEmpty());
        assertEquals(rides.get(0).getStatus(),status);
    }

    @Test
    public void shouldNotFindRideIfDateIsBeforeStartDate() {

        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(20, ChronoUnit.MINUTES)));

        RideStatus status = RideStatus.FINISHED;
        Ride ride1 = new Ride();
        ride1.setEndTime(test_time);
        ride1.setStatus(status);

        rideRepository.save(ride1);

        List<Ride> rides = rideRepository.findRidesWithStatusBetweenDates(status,interval_start,interval_end);
        assertTrue(rides.isEmpty());

    }

    @Test
    public void shouldFindRideIfRideEndsAtStartDate() {

        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));

        RideStatus status = RideStatus.FINISHED;
        Ride ride1 = new Ride();
        ride1.setEndTime(test_time);
        ride1.setStatus(status);

        rideRepository.save(ride1);

        List<Ride> rides = rideRepository.findRidesWithStatusBetweenDates(status,interval_start,interval_end);
        assertFalse(rides.isEmpty());
    }

    @Test
    public void shouldFindRideIfRideEndsBetweenStartAndEndDate() {

        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(50, ChronoUnit.MINUTES)));

        RideStatus status = RideStatus.FINISHED;
        Ride ride1 = new Ride();
        ride1.setEndTime(test_time);
        ride1.setStatus(status);

        rideRepository.save(ride1);

        List<Ride> rides = rideRepository.findRidesWithStatusBetweenDates(status,interval_start,interval_end);
        assertFalse(rides.isEmpty());
    }

    @Test
    public void shouldFindRideIfRideEndsAtEndDate() {

        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));

        RideStatus status = RideStatus.FINISHED;
        Ride ride1 = new Ride();
        ride1.setEndTime(test_time);
        ride1.setStatus(status);

        rideRepository.save(ride1);

        List<Ride> rides = rideRepository.findRidesWithStatusBetweenDates(status,interval_start,interval_end);
        assertFalse(rides.isEmpty());
    }

    @Test
    public void shouldNotFindRideIfRideEndsAfterEndDate() {

        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(70, ChronoUnit.MINUTES)));

        RideStatus status = RideStatus.FINISHED;
        Ride ride1 = new Ride();
        ride1.setEndTime(test_time);
        ride1.setStatus(status);

        rideRepository.save(ride1);

        List<Ride> rides = rideRepository.findRidesWithStatusBetweenDates(status,interval_start,interval_end);
        assertTrue(rides.isEmpty());
    }

    @Test
    public void shouldOnlyFindRidesThatAreInTimeInterval() {

        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(80, ChronoUnit.MINUTES)));
        Date test_time1 = Date.from(now.toInstant().plus(Duration.of(20, ChronoUnit.MINUTES)));
        Date test_time2 = Date.from(now.toInstant().plus(Duration.of(50, ChronoUnit.MINUTES)));
        Date test_time3 = Date.from(now.toInstant().plus(Duration.of(90, ChronoUnit.MINUTES)));

        RideStatus status = RideStatus.FINISHED;
        Ride ride1 = new Ride();
        ride1.setEndTime(test_time1);
        ride1.setStatus(status);

        Ride ride2 = new Ride();
        ride2.setEndTime(test_time2);
        ride2.setStatus(status);

        Ride ride3 = new Ride();
        ride3.setEndTime(test_time3);
        ride3.setStatus(status);

        rideRepository.save(ride1);
        rideRepository.save(ride2);
        rideRepository.save(ride3);

        List<Ride> rides = rideRepository.findRidesWithStatusBetweenDates(status,interval_start,interval_end);
        assertFalse(rides.isEmpty());
        assertEquals(rides.size(),1);
    }

    @Test
    public void shouldOnlyRidesWithSelectStatus() {

        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(80, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));

        for (RideStatus s : RideStatus.values())
        {
            Ride ride = new Ride();
            ride.setEndTime(test_time);
            ride.setStatus(s);
            rideRepository.save(ride);
        }

        RideStatus status = RideStatus.FINISHED;

        List<Ride> rides = rideRepository.findRidesWithStatusBetweenDates(status,interval_start,interval_end);
        assertFalse(rides.isEmpty());
        assertEquals(rides.size(),1);
    }


}