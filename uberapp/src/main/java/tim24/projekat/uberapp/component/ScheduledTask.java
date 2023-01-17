package tim24.projekat.uberapp.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import tim24.projekat.uberapp.service.RideService;

@Component
public class ScheduledTask {

    @Autowired
    private RideService rideService;

    @Scheduled(fixedRate = 1 * 60 * 1000)
    public void checkForScheduledRides() {
        rideService.assignDriverToScheduledRide();
    }
    
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void notifyPassengerForImpendingRide() {
        System.out.println("pogledati voznje i obavestiti");
    }
}

