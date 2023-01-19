package tim24.projekat.uberapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tim24.projekat.uberapp.DTO.DTOList;
import tim24.projekat.uberapp.DTO.ErrorDTO;
import tim24.projekat.uberapp.DTO.NotificationDTO;
import tim24.projekat.uberapp.DTO.NotificationRequestDTO;
import tim24.projekat.uberapp.exception.ConditionNotMetException;
import tim24.projekat.uberapp.exception.ObjectNotFoundException;
import tim24.projekat.uberapp.service.NotificationService;

@RestController
@RequestMapping("api/notification")
public class NotificationController
{
    @Autowired
    private NotificationService notificationService;

    @GetMapping("")
    public ResponseEntity<?> getUnreadNotifications()
    {
        DTOList<NotificationDTO> list = notificationService.getUnreadNotifications();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> postNotification(@RequestBody NotificationRequestDTO notificationRequestDTO)
    {
        try
        {
            notificationService.postNotification(notificationRequestDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(ObjectNotFoundException e)
        {
            ErrorDTO error = new ErrorDTO(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/read/{id}")
    public ResponseEntity<?> readNotification(@PathVariable("id") Long id)
    {
        try
        {
            notificationService.readNotification(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(ObjectNotFoundException e)
        {
            ErrorDTO error = new ErrorDTO(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        catch(ConditionNotMetException e)
        {
            ErrorDTO error = new ErrorDTO(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}
