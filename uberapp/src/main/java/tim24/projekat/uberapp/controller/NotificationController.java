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
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.security.JwtTokenUtil;
import tim24.projekat.uberapp.service.NotificationService;
import tim24.projekat.uberapp.service.UserService;

@RestController
@RequestMapping("api/notification")
public class NotificationController
{
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;

    @GetMapping("/unread")
    public ResponseEntity<?> getHasUnread(@RequestHeader("Authorization") String auth)
    {
        try
        {
            String userMail = jwtTokenUtil.getUsernameFromToken(auth.substring(7));
            User user = userService.findUserByEmail(userMail);
            Long id = user.getId();
            boolean bool = notificationService.getHasUnread(id);
            return new ResponseEntity<>(bool, HttpStatus.OK);
        }
        catch(ObjectNotFoundException e)
        {
            ErrorDTO error = new ErrorDTO(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getNotificationsForUser(@RequestHeader("Authorization") String auth)
    {
        try
        {
            String userMail = jwtTokenUtil.getUsernameFromToken(auth.substring(7));
            User user = userService.findUserByEmail(userMail);
            Long id = user.getId();
            DTOList<NotificationDTO> list = notificationService.getNotificationsById(id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch(ObjectNotFoundException e)
        {
            ErrorDTO error = new ErrorDTO(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
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
