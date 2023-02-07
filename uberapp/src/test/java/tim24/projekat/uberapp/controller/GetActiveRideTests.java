package tim24.projekat.uberapp.controller;

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
public class GetActiveRideTests {
	
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
    public void getDriverActiveRide_HappyFlow() throws Exception {

		String jwt = jwtTokenUtil.generateToken("vlafa@mail.com");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/ride") //poruci voznju
        		.header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk()); 

		 mockMvc.perform(MockMvcRequestBuilders.get("/api/ride/driver/3/active") 
	        		.header("Authorization", "Bearer " + jwt))
	                .andExpect(MockMvcResultMatchers.status().isOk()); 
		
    }
	@Test
    public void getDriverActiveRide_withoutAuthorization_returnsForbidden() throws Exception {

		 mockMvc.perform(MockMvcRequestBuilders.get("/api/ride/driver/2/active"))
	                .andExpect(MockMvcResultMatchers.status().isForbidden()); 
		
    }
	@Test
    public void getDriverActiveRide_withoutActiveRide_returnsNotFound() throws Exception {
		String jwt = jwtTokenUtil.generateToken("vlafa@mail.com");

		 mockMvc.perform(MockMvcRequestBuilders.get("/api/ride/passenger/3/active")
	        		.header("Authorization", "Bearer " + jwt))
	                .andExpect(MockMvcResultMatchers.status().isNotFound()); 
    }
	@Test
    public void getDriverActiveRide_invalidUserId_returnsNotFound() throws Exception {
		String jwt = jwtTokenUtil.generateToken("vlafa@mail.com");

		 mockMvc.perform(MockMvcRequestBuilders.get("/api/ride/passenger/123/active")
	        		.header("Authorization", "Bearer " + jwt))
	                .andExpect(MockMvcResultMatchers.status().isNotFound()); 
		
    }
	
	@Test
    public void getPassengerActiveRide_HappyFlow() throws Exception {

		String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/ride") //poruci voznju
        		.header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk()); 
		
		 mockMvc.perform(MockMvcRequestBuilders.get("/api/ride/passenger/2/active") //vidi jel ima
	        		.header("Authorization", "Bearer " + jwt))
	                .andExpect(MockMvcResultMatchers.status().isOk()); 
		
    }
	@Test
    public void getPassengerActiveRide_withoutAuthorization_returnsForbidden() throws Exception {

		 mockMvc.perform(MockMvcRequestBuilders.get("/api/ride/passenger/2/active"))
	                .andExpect(MockMvcResultMatchers.status().isForbidden()); 
		
    }
	@Test
    public void getPassengerActiveRide_withoutActiveRide_returnsNotFound() throws Exception {
		String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");

		 mockMvc.perform(MockMvcRequestBuilders.get("/api/ride/passenger/2/active")
	        		.header("Authorization", "Bearer " + jwt))
	                .andExpect(MockMvcResultMatchers.status().isNotFound()); 
		
    }
	@Test
    public void getPassengerActiveRide_invalidUserId_returnsNotFound() throws Exception {
		String jwt = jwtTokenUtil.generateToken("stefanium@mail.com");

		 mockMvc.perform(MockMvcRequestBuilders.get("/api/ride/passenger/123/active")
	        		.header("Authorization", "Bearer " + jwt))
	                .andExpect(MockMvcResultMatchers.status().isNotFound()); 
		
    }

}
