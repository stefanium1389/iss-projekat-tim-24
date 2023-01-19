package tim24.projekat.uberapp.service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtils {

	public Date parseDate (String date) 
	{
		Instant instant = Instant.parse(date);
		Date parsedDate = Date.from(instant);
		return parsedDate;
	}
	
	public String formatDate (Date date) 
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		return sdf.format(date);
	}
	
}