package tim24.projekat.uberapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.VehicleDTO;
import tim24.projekat.uberapp.model.Vehicle;
import tim24.projekat.uberapp.repo.VehicleRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class WebSocketController
{
    @Autowired
    public SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private VehicleRepository vehicleRepo;

    @MessageMapping("/send/notification")
    public Map<String, String> broadcastNotification(String message)
    {
        Map<String, String> messageConverted = parseMessage(message);
        this.simpMessagingTemplate.convertAndSend("/notification/" + messageConverted.get("userId"), messageConverted);
        return messageConverted;
    }

    /*@Scheduled(fixedRate = 5000)
    public void broadcastMap()
    {
        List<Vehicle> vehicles = vehicleRepo.findAll();
        DTOList list = new DTOList();
        for(Vehicle v: vehicles)
            list.add(new VehicleDTO(v));
        this.simpMessagingTemplate.convertAndSend("/map", list);
    }*/

    @SuppressWarnings("unchecked")
    private Map<String, String> parseMessage(String message)
    {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> retVal;

        try {
            retVal = mapper.readValue(message, Map.class); // parsiranje JSON stringa
        } catch (IOException e) {
            retVal = null;
        }

        return retVal;
    }
}
