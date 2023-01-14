package tim24.projekat.uberapp.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailingService {
	
	@Value("${spring.mail.username}")
    private String from;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Async
	public void sendEmail(String to, String subject, String body) throws MessagingException {
	    MimeMessage message = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
	    helper.setFrom(from); // <--- THIS IS IMPORTANT FOR OUTLOOK!
	    helper.setTo(to);
	    helper.setSubject(subject);
	    helper.setText(body);
	    javaMailSender.send(message);
	}
	
	public void sendActivationEmail(String email, String token) throws MessagingException {		
		String body = "To verify your email click on the following link http://localhost:4200/activate?token="+token;
		sendEmail(email,"UberApp Tim24 Email Validation",body);
	}
	
	

}
