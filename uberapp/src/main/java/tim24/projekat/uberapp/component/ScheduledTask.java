package tim24.projekat.uberapp.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import tim24.projekat.uberapp.DTO.NotificationRequestDTO;
import tim24.projekat.uberapp.DTO.VehicleDTO;
import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.controller.WebSocketController;
import tim24.projekat.uberapp.model.Ride;
import tim24.projekat.uberapp.model.RideStatus;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.model.Vehicle;
import tim24.projekat.uberapp.repo.RideRepository;
import tim24.projekat.uberapp.repo.VehicleRepository;
import tim24.projekat.uberapp.service.NotificationService;
import tim24.projekat.uberapp.service.RideService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTask {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private RideService rideService;
    @Autowired
    private WebSocketController webSocketController;
    @Autowired
    private RideRepository rideRepo;
    @Autowired
    private VehicleRepository vehicleRepo;

    @Scheduled(fixedRate = 1 * 60 * 1000)
    public void checkForScheduledRides()
    {
        rideService.assignDriverToScheduledRide();
    }
    
    @Scheduled(fixedRate = 1 * 60 * 1000)
    public void notifyPassengerForImpendingRide()
    {
        System.out.println("pogledati voznje i obavestiti");
        List<Ride> rides = rideRepo.findAll();
        for(Ride ride: rides)
        {
            if(ride.getStatus() == RideStatus.ACCEPTED)
            {
                Date now = new Date();
                Date rideStertTime = ride.getStartTime();
                Calendar nowCal = Calendar.getInstance();
                nowCal.setTime(now);

                //pocinje za 5 minuta
                nowCal.add(Calendar.MINUTE, 4);
                now = nowCal.getTime();
                if(now.compareTo(rideStertTime) < 0)
                {
                    nowCal.add(Calendar.MINUTE, 1);
                    now = nowCal.getTime();
                    if(now.compareTo(rideStertTime) >= 0)
                    {
                        for(User passenger: ride.getPassengers())
                        {
                            NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO(passenger.getId(), "Your scheduled ride starts within 5 minutes.", "NORMAL");
                            notificationService.postNotification(notificationRequestDTO);
                        }
                    }
                }

                //pocinje za 10 minuta
                nowCal.add(Calendar.MINUTE, 4);
                now = nowCal.getTime();
                if(now.compareTo(rideStertTime) < 0)
                {
                    nowCal.add(Calendar.MINUTE, 1);
                    now = nowCal.getTime();
                    if(now.compareTo(rideStertTime) >= 0)
                    {
                        for(User passenger: ride.getPassengers())
                        {
                            NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO(passenger.getId(), "Your scheduled ride starts within 10 minutes.", "NORMAL");
                            notificationService.postNotification(notificationRequestDTO);
                        }
                    }
                }

                //pocinje za 15 minuta
                nowCal.add(Calendar.MINUTE, 4);
                now = nowCal.getTime();
                if(now.compareTo(rideStertTime) < 0)
                {
                    nowCal.add(Calendar.MINUTE, 1);
                    now = nowCal.getTime();
                    if(now.compareTo(rideStertTime) >= 0)
                    {
                        for(User passenger: ride.getPassengers())
                        {
                            NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO(passenger.getId(), "Your scheduled ride starts within 15 minutes.", "NORMAL");
                            notificationService.postNotification(notificationRequestDTO);
                        }
                    }
                }
            }
        }
    }

    @Scheduled(fixedRate = 5 * 1000)
    public void broadcastMap()
    {
        List<Vehicle> vehicles = vehicleRepo.findAll();
        DTOList list = new DTOList();
        for(Vehicle v: vehicles)
            list.add(new VehicleDTO(v));
        webSocketController.simpMessagingTemplate.convertAndSend("/map", list);
    }
}

