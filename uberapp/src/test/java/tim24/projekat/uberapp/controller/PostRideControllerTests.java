package tim24.projekat.uberapp.controller;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import tim24.projekat.uberapp.security.JwtTokenUtil;



@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("classpath:testdata.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) 
public class PostRideControllerTests { 
	//proveri jos:
	//kad nema vozila (aktivnih vozaca nema), 
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	String requestJson = "{\r\n"
			+ "  \"locations\": [\r\n"
			+ "    {\r\n"
			+ "      \"departure\": {\r\n"
			+ "        \"address\": \"Bulevar oslobodjenja 46\",\r\n"
			+ "        \"latitude\": 45.267136,\r\n"
			+ "        \"longitude\": 19.833549\r\n"
			+ "      },\r\n"
			+ "      \"destination\": {\r\n"
			+ "        \"address\": \"Bulevar oslobodjenja 46\",\r\n"
			+ "        \"latitude\": 45.267136,\r\n"
			+ "        \"longitude\": 19.833549\r\n"
			+ "      }\r\n"
			+ "    }\r\n"
			+ "  ],\r\n"
			+ "  \"passengers\": [\r\n"
			+ "    {\r\n"
			+ "      \"id\": 2,\r\n"
			+ "      \"email\": \"stefanium@mail.com\"\r\n"
			+ "    }\r\n"
			+ "  ],\r\n"
			+ "  \"vehicleType\": \"STANDARD\",\r\n"
			+ "  \"babyTransport\": false,\r\n"
			+ "  \"petTransport\": false,\r\n"
			+ "  \"scheduledTime\": null\r\n"
			+ "}";

	@Test
    public void postRide_withoutValidAuthorization_returnsForbidden() throws Exception {
		
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride") //poruci voznju bez tokena
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
		
	}
	@Test
    public void postRide_NoValidDriver_returnsBadRequest() throws Exception {

		String requestJson = "{\r\n"
				+ "  \"locations\": [\r\n"
				+ "    {\r\n"
				+ "      \"departure\": {\r\n"
				+ "        \"address\": \"Bulevar oslobodjenja 46\",\r\n"
				+ "        \"latitude\": 45.267136,\r\n"
				+ "        \"longitude\": 19.833549\r\n"
				+ "      },\r\n"
				+ "      \"destination\": {\r\n"
				+ "        \"address\": \"Bulevar oslobodjenja 46\",\r\n"
				+ "        \"latitude\": 45.267136,\r\n"
				+ "        \"longitude\": 19.833549\r\n"
				+ "      }\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"passengers\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": 2,\r\n"
				+ "      \"email\": \"stefanium@mail.com\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"vehicleType\": \"STANDARD\",\r\n"
				+ "  \"babyTransport\": true,\r\n"					// nemamo nikog ko vozi bebe, pa nece naci vozaca
				+ "  \"petTransport\": true,\r\n"
				+ "  \"scheduledTime\": null\r\n"
				+ "}";
		
        String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
        String status = "ACCEPTED";
        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride") //poruci voznju
        		.header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); 
		
	}
	@Test
    public void postRide_HappyFlow() throws Exception {
		
        String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
        String status = "ACCEPTED";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride") //poruci voznju
        		.header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(status));
		
	}
	
	@Test
    public void postRide_WhileHasPendingRide_returnsBadRequest() throws Exception {
		
        String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
        String status = "ACCEPTED";
        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride") //poruci voznju
        		.header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk()); 
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride") //poruci jos jednom kad imas vec
        		.header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); 
		
	}
	
	@Test
    public void postRide_InvalidPassenger_returnsBadRequest() throws Exception {
		
        String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
        String status = "ACCEPTED";
        
        String requestJson = "{\r\n"
    			+ "  \"locations\": [\r\n"
    			+ "    {\r\n"
    			+ "      \"departure\": {\r\n"
    			+ "        \"address\": \"Bulevar oslobodjenja 46\",\r\n"
    			+ "        \"latitude\": 45.267136,\r\n"
    			+ "        \"longitude\": 19.833549\r\n"
    			+ "      },\r\n"
    			+ "      \"destination\": {\r\n"
    			+ "        \"address\": \"Bulevar oslobodjenja 46\",\r\n"
    			+ "        \"latitude\": 45.267136,\r\n"
    			+ "        \"longitude\": 19.833549\r\n"
    			+ "      }\r\n"
    			+ "    }\r\n"
    			+ "  ],\r\n"
    			+ "  \"passengers\": [\r\n"
    			+ "    {\r\n"
    			+ "      \"id\": 123,\r\n"
    			+ "      \"email\": \"sdkfzintgzd@alealeale.com\"\r\n"
    			+ "    }\r\n"
    			+ "  ],\r\n"
    			+ "  \"vehicleType\": \"STANDARD\",\r\n"
    			+ "  \"babyTransport\": false,\r\n"
    			+ "  \"petTransport\": false,\r\n"
    			+ "  \"scheduledTime\": null\r\n"
    			+ "}";
        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride") //poruci voznju
        		.header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound()); 
        
	}
	@Test
    public void postRide_InvalidLocations_returnsNotFound() throws Exception {
		
        String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
        
        String requestJson = "{\r\n"
    			+ "  \"locations\": [\r\n"
    			+ "    {\r\n"
    			+ "      \"departure\": {\r\n"
    			+ "        \"address\": \"Bulevar oslobodjenja 46\",\r\n"
    			+ "        \"latitude\": 90.267136,\r\n"
    			+ "        \"longitude\": 90.833549\r\n"
    			+ "      },\r\n"
    			+ "      \"destination\": {\r\n"
    			+ "        \"address\": \"Bulevar oslobodjenja 46\",\r\n"
    			+ "        \"latitude\": 90.267136,\r\n"
    			+ "        \"longitude\": 90.833549\r\n"
    			+ "      }\r\n"
    			+ "    }\r\n"
    			+ "  ],\r\n"
    			+ "  \"passengers\": [\r\n"
    			+ "    {\r\n"
    			+ "      \"id\": 123,\r\n"
    			+ "      \"email\": \"sdkfzintgzd@alealeale.com\"\r\n"
    			+ "    }\r\n"
    			+ "  ],\r\n"
    			+ "  \"vehicleType\": \"STANDARD\",\r\n"
    			+ "  \"babyTransport\": false,\r\n"
    			+ "  \"petTransport\": false,\r\n"
    			+ "  \"scheduledTime\": null\r\n"
    			+ "}";
        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride") //poruci voznju
        		.header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound()); 
        
	}
	@Test
    public void postRide_InvalidVehicleType_returnsBadRequest() throws Exception {

		String requestJson = "{\r\n"
				+ "  \"locations\": [\r\n"
				+ "    {\r\n"
				+ "      \"departure\": {\r\n"
				+ "        \"address\": \"Bulevar oslobodjenja 46\",\r\n"
				+ "        \"latitude\": 45.267136,\r\n"
				+ "        \"longitude\": 19.833549\r\n"
				+ "      },\r\n"
				+ "      \"destination\": {\r\n"
				+ "        \"address\": \"Bulevar oslobodjenja 46\",\r\n"
				+ "        \"latitude\": 45.267136,\r\n"
				+ "        \"longitude\": 19.833549\r\n"
				+ "      }\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"passengers\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": 2,\r\n"
				+ "      \"email\": \"stefanium@mail.com\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"vehicleType\": \"SDKFZ\",\r\n"				//ovaj tip ne postoji
				+ "  \"babyTransport\": false,\r\n"					
				+ "  \"petTransport\": false,\r\n"
				+ "  \"scheduledTime\": null\r\n"
				+ "}";
		
        String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
        String status = "ACCEPTED";
        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride") //poruci voznju
        		.header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); 
		
	}
	@Test
    public void postRide_scheduledRideDateRightNow_returnsBadRequest() throws Exception {
		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		//now = Date.from(now.toInstant().minus(Duration.ofSeconds(1)));
		String past_date = sdf.format(now);

		String requestJson = "{\r\n"
				+ "  \"locations\": [\r\n"
				+ "    {\r\n"
				+ "      \"departure\": {\r\n"
				+ "        \"address\": \"Bulevar oslobodjenja 46\",\r\n"
				+ "        \"latitude\": 45.267136,\r\n"
				+ "        \"longitude\": 19.833549\r\n"
				+ "      },\r\n"
				+ "      \"destination\": {\r\n"
				+ "        \"address\": \"Bulevar oslobodjenja 46\",\r\n"
				+ "        \"latitude\": 45.267136,\r\n"
				+ "        \"longitude\": 19.833549\r\n"
				+ "      }\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"passengers\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": 2,\r\n"
				+ "      \"email\": \"stefanium@mail.com\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"vehicleType\": \"STANDARD\",\r\n"				
				+ "  \"babyTransport\": false,\r\n"					
				+ "  \"petTransport\": false,\r\n"
				+ "  \"scheduledTime\": "+"\""+past_date+"\""+"\r\n"
				+ "}";
		
        String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride") //poruci voznju
        		.header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); 
		
	}
	@Test
    public void postRide_scheduledRideDateInThePast_returnsBadRequest() throws Exception {
		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		now = Date.from(now.toInstant().minus(Duration.ofSeconds(1)));
		String past_date = sdf.format(now);

		String requestJson = "{\r\n"
				+ "  \"locations\": [\r\n"
				+ "    {\r\n"
				+ "      \"departure\": {\r\n"
				+ "        \"address\": \"Bulevar oslobodjenja 46\",\r\n"
				+ "        \"latitude\": 45.267136,\r\n"
				+ "        \"longitude\": 19.833549\r\n"
				+ "      },\r\n"
				+ "      \"destination\": {\r\n"
				+ "        \"address\": \"Bulevar oslobodjenja 46\",\r\n"
				+ "        \"latitude\": 45.267136,\r\n"
				+ "        \"longitude\": 19.833549\r\n"
				+ "      }\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"passengers\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": 2,\r\n"
				+ "      \"email\": \"stefanium@mail.com\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"vehicleType\": \"STANDARD\",\r\n"				
				+ "  \"babyTransport\": false,\r\n"					
				+ "  \"petTransport\": false,\r\n"
				+ "  \"scheduledTime\": "+"\""+past_date+"\""+"\r\n"
				+ "}";
		
        String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride") //poruci voznju
        		.header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); 
		
	}
	@Test
    public void postRide_scheduledRideDateMoreThen5HoursInFuture_returnsBadRequest() throws Exception {
		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		now = Date.from(now.toInstant().plus(Duration.ofHours(5).plus(Duration.ofSeconds(1)))); //za milisekunde vrati da je ok, a sta je 1 mili uopste?
		String future_date = sdf.format(now);

		String requestJson = "{\r\n"
				+ "  \"locations\": [\r\n"
				+ "    {\r\n"
				+ "      \"departure\": {\r\n"
				+ "        \"address\": \"Bulevar oslobodjenja 46\",\r\n"
				+ "        \"latitude\": 45.267136,\r\n"
				+ "        \"longitude\": 19.833549\r\n"
				+ "      },\r\n"
				+ "      \"destination\": {\r\n"
				+ "        \"address\": \"Bulevar oslobodjenja 46\",\r\n"
				+ "        \"latitude\": 45.267136,\r\n"
				+ "        \"longitude\": 19.833549\r\n"
				+ "      }\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"passengers\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": 2,\r\n"
				+ "      \"email\": \"stefanium@mail.com\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"vehicleType\": \"STANDARD\",\r\n"				
				+ "  \"babyTransport\": false,\r\n"					
				+ "  \"petTransport\": false,\r\n"
				+ "  \"scheduledTime\": "+"\""+future_date+"\""+"\r\n"
				+ "}";
		
        String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride") //poruci voznju
        		.header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); 
		
	}
	@Test
    public void postRide_scheduledTimeHappyFlow() throws Exception {
		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		now = Date.from(now.toInstant().plus(Duration.ofHours(1)));
		String future_date = sdf.format(now);

		String requestJson = "{\r\n"
				+ "  \"locations\": [\r\n"
				+ "    {\r\n"
				+ "      \"departure\": {\r\n"
				+ "        \"address\": \"Bulevar oslobodjenja 46\",\r\n"
				+ "        \"latitude\": 45.267136,\r\n"
				+ "        \"longitude\": 19.833549\r\n"
				+ "      },\r\n"
				+ "      \"destination\": {\r\n"
				+ "        \"address\": \"Bulevar oslobodjenja 46\",\r\n"
				+ "        \"latitude\": 45.267136,\r\n"
				+ "        \"longitude\": 19.833549\r\n"
				+ "      }\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"passengers\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": 2,\r\n"
				+ "      \"email\": \"stefanium@mail.com\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"vehicleType\": \"STANDARD\",\r\n"				
				+ "  \"babyTransport\": false,\r\n"					
				+ "  \"petTransport\": false,\r\n"
				+ "  \"scheduledTime\": "+"\""+future_date+"\""+"\r\n"
				+ "}";
		
        String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
        String status = "PENDING";
        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride") //poruci voznju
        		.header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(status));

		
	}
}


