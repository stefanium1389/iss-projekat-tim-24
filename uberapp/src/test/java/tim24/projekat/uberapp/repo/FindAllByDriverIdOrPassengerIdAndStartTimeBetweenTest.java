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
public class FindAllByDriverIdOrPassengerIdAndStartTimeBetweenTest {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindRidesForPassengerIfRidesAreInIntervalAndBelongToPassenger() {

        User user = new User();
        List<User> passenger_list = new ArrayList<>();
        passenger_list.add(user);
        Date now = new Date();
        Date middle = Date.from(now.toInstant().plus(Duration.of(45, ChronoUnit.MINUTES)));
        Date end = Date.from(now.toInstant().plus(Duration.of(90, ChronoUnit.MINUTES)));
        Pageable pageable = PageRequest.of(0, 100);

        Ride ride1 = new Ride();
        ride1.setPassengers(passenger_list);
        ride1.setStartTime(middle);

        userRepository.save(user);
        rideRepository.save(ride1);

        User passenger = userRepository.findUserById(1L).orElse(null);
        assertNotNull(passenger);

        Page<Ride> rides = rideRepository.findByPassengerIdAndStartTimeBetween(passenger.getId(),now,end,pageable);
        assertFalse(rides.isEmpty());
        assertEquals(rides.getNumberOfElements(),1);

    }

    @Test
    public void shouldNotFindRideIfRideStartsBeforeStartDatePassenger() {

        User user = new User();
        List<User> passenger_list = new ArrayList<>();
        passenger_list.add(user);
        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(20, ChronoUnit.MINUTES)));

        Pageable pageable = PageRequest.of(0, 100);

        Ride ride1 = new Ride();
        ride1.setPassengers(passenger_list);
        ride1.setStartTime(test_time);

        userRepository.save(user);
        rideRepository.save(ride1);

        User passenger = userRepository.findUserById(1L).orElse(null);
        assertNotNull(passenger);

        Page<Ride> rides = rideRepository.findByPassengerIdAndStartTimeBetween(passenger.getId(),interval_start,interval_end,pageable);
        assertTrue(rides.isEmpty());

    }

    @Test
    public void shouldFindRideIfRideStartsAtStartDatePassenger() {

        User user = new User();
        List<User> passenger_list = new ArrayList<>();
        passenger_list.add(user);
        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));

        Pageable pageable = PageRequest.of(0, 100);

        Ride ride1 = new Ride();
        ride1.setPassengers(passenger_list);
        ride1.setStartTime(test_time);

        userRepository.save(user);
        rideRepository.save(ride1);

        User passenger = userRepository.findUserById(1L).orElse(null);
        assertNotNull(passenger);

        Page<Ride> rides = rideRepository.findByPassengerIdAndStartTimeBetween(passenger.getId(),interval_start,interval_end,pageable);
        assertFalse(rides.isEmpty());
    }

    @Test
    public void shouldFindRideIfRideStartsBetweenStartAndEndDatePassenger() {

        User user = new User();
        List<User> passenger_list = new ArrayList<>();
        passenger_list.add(user);
        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(50, ChronoUnit.MINUTES)));

        Pageable pageable = PageRequest.of(0, 100);

        Ride ride1 = new Ride();
        ride1.setPassengers(passenger_list);
        ride1.setStartTime(test_time);

        userRepository.save(user);
        rideRepository.save(ride1);

        User passenger = userRepository.findUserById(1L).orElse(null);
        assertNotNull(passenger);

        Page<Ride> rides = rideRepository.findByPassengerIdAndStartTimeBetween(passenger.getId(),interval_start,interval_end,pageable);
        assertFalse(rides.isEmpty());
    }

    @Test
    public void shouldFindRideIfRideStartsAtEndDatePassenger() {

        User user = new User();
        List<User> passenger_list = new ArrayList<>();
        passenger_list.add(user);
        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));

        Pageable pageable = PageRequest.of(0, 100);

        Ride ride1 = new Ride();
        ride1.setPassengers(passenger_list);
        ride1.setStartTime(test_time);

        userRepository.save(user);
        rideRepository.save(ride1);

        User passenger = userRepository.findUserById(1L).orElse(null);
        assertNotNull(passenger);

        Page<Ride> rides = rideRepository.findByPassengerIdAndStartTimeBetween(passenger.getId(),interval_start,interval_end,pageable);
        assertFalse(rides.isEmpty());
    }

    @Test
    public void shouldNotFindRideIfRideStartsAfterEndDatePassenger() {

        User user = new User();
        List<User> passenger_list = new ArrayList<>();
        passenger_list.add(user);
        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(70, ChronoUnit.MINUTES)));

        Pageable pageable = PageRequest.of(0, 100);

        Ride ride1 = new Ride();
        ride1.setPassengers(passenger_list);
        ride1.setStartTime(test_time);

        userRepository.save(user);
        rideRepository.save(ride1);

        User passenger = userRepository.findUserById(1L).orElse(null);
        assertNotNull(passenger);

        Page<Ride> rides = rideRepository.findByPassengerIdAndStartTimeBetween(passenger.getId(),interval_start,interval_end,pageable);
        assertTrue(rides.isEmpty());
    }

    @Test
    public void shouldOnlyFindRidesThatAreInTimeIntervalPassenger() {

        User user = new User();
        List<User> passenger_list = new ArrayList<>();
        passenger_list.add(user);
        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));
        Date time1 = Date.from(now.toInstant().plus(Duration.of(20, ChronoUnit.MINUTES)));
        Date time2 = Date.from(now.toInstant().plus(Duration.of(50, ChronoUnit.MINUTES)));
        Date time3 = Date.from(now.toInstant().plus(Duration.of(90, ChronoUnit.MINUTES)));

        Pageable pageable = PageRequest.of(0, 100);

        Ride ride1 = new Ride();
        ride1.setPassengers(passenger_list);
        ride1.setStartTime(time1);

        Ride ride2 = new Ride();
        ride2.setPassengers(passenger_list);
        ride2.setStartTime(time2);

        Ride ride3 = new Ride();
        ride3.setPassengers(passenger_list);
        ride3.setStartTime(time3);

        userRepository.save(user);
        rideRepository.save(ride1);
        rideRepository.save(ride2);
        rideRepository.save(ride3);

        User passenger = userRepository.findUserById(1L).orElse(null);
        assertNotNull(passenger);

        Page<Ride> rides = rideRepository.findByPassengerIdAndStartTimeBetween(passenger.getId(),interval_start,interval_end,pageable);
        assertFalse(rides.isEmpty());
        assertEquals(rides.getNumberOfElements(),1);
    }

    @Test
    public void shouldOnlyFindRidesThatAreInTimeIntervalAndBelongToPassenger() {

        User user = new User();
        List<User> passenger_list = new ArrayList<>();
        passenger_list.add(user);

        User user2 = new User();
        List<User> passenger_list2 = new ArrayList<>();
        passenger_list2.add(user2);

        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));
        Date time1 = Date.from(now.toInstant().plus(Duration.of(20, ChronoUnit.MINUTES)));
        Date time2 = Date.from(now.toInstant().plus(Duration.of(50, ChronoUnit.MINUTES)));
        Date time3 = Date.from(now.toInstant().plus(Duration.of(90, ChronoUnit.MINUTES)));

        Pageable pageable = PageRequest.of(0, 100);

        Ride ride1 = new Ride();
        ride1.setPassengers(passenger_list);
        ride1.setStartTime(time1);

        Ride ride2 = new Ride();
        ride2.setPassengers(passenger_list);
        ride2.setStartTime(time2);

        Ride ride2passenger2 = new Ride();
        ride2passenger2.setPassengers(passenger_list2);
        ride2passenger2.setStartTime(time2);

        Ride ride3 = new Ride();
        ride3.setPassengers(passenger_list);
        ride3.setStartTime(time3);

        userRepository.save(user);
        userRepository.save(user2);
        rideRepository.save(ride1);
        rideRepository.save(ride2);
        rideRepository.save(ride2passenger2);
        rideRepository.save(ride3);

        User passenger = userRepository.findUserById(1L).orElse(null);
        assertNotNull(passenger);

        Page<Ride> rides = rideRepository.findByPassengerIdAndStartTimeBetween(passenger.getId(),interval_start,interval_end,pageable);
        assertFalse(rides.isEmpty());
        assertEquals(rides.getNumberOfElements(),1);
    }

    @Test
    public void shouldFindRidesForPassengerIfThereAreMultiplePassengersInRide() {

        User user = new User();
        List<User> passenger_list = new ArrayList<>();
        passenger_list.add(user);

        User user2 = new User();
        List<User> passenger_list_multiple = new ArrayList<>();
        passenger_list_multiple.add(user);
        passenger_list_multiple.add(user2);

        Date now = new Date();
        Date interval_start = Date.from(now.toInstant().plus(Duration.of(40, ChronoUnit.MINUTES)));
        Date interval_end = Date.from(now.toInstant().plus(Duration.of(60, ChronoUnit.MINUTES)));
        Date test_time = Date.from(now.toInstant().plus(Duration.of(50, ChronoUnit.MINUTES)));


        Pageable pageable = PageRequest.of(0, 100);

        Ride ride1 = new Ride();
        ride1.setPassengers(passenger_list);
        ride1.setStartTime(test_time);

        Ride ride2 = new Ride();
        ride2.setPassengers(passenger_list_multiple);
        ride2.setStartTime(test_time);

        Ride ride3 = new Ride();
        ride3.setPassengers(passenger_list_multiple);
        ride3.setStartTime(test_time);

        userRepository.save(user);
        userRepository.save(user2);
        rideRepository.save(ride1);
        rideRepository.save(ride2);
        rideRepository.save(ride3);

        User passenger1 = userRepository.findUserById(user.getId()).orElse(null);
        assertNotNull(passenger1);
        User passenger2 = userRepository.findUserById(user2.getId()).orElse(null);
        assertNotNull(passenger2);

        Page<Ride> rides = rideRepository.findByPassengerIdAndStartTimeBetween(passenger1.getId(),interval_start,interval_end,pageable);
        assertFalse(rides.isEmpty());
        assertEquals(rides.getNumberOfElements(),3);

        rides = rideRepository.findByPassengerIdAndStartTimeBetween(passenger2.getId(),interval_start,interval_end,pageable);
        assertFalse(rides.isEmpty());
        assertEquals(rides.getNumberOfElements(),2);
    }

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
    public void shouldNotFindRideIfRideStartsBeforeStartDateDriver() {

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
    public void shouldFindRideIfRideStartsAtStartDateDriver() {

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
    public void shouldFindRideIfRideStartsBetweenStartAndEndDateDriver() {

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
    public void shouldFindRideIfRideStartsAtEndDateDriver() {

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
    public void shouldNotFindRideIfRideStartsAfterEndDateDriver() {

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
    public void shouldOnlyFindRidesThatAreInTimeIntervalDriver() {

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