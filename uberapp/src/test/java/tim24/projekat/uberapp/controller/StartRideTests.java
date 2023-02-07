package tim24.projekat.uberapp.controller;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
public class StartRideTests {
	
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

	@BeforeEach
    public void setUpActiveRide() throws Exception {
		
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
	public void startRide_HappyFlow() throws Exception {
		
        String jwt = jwtTokenUtil.generateToken("vlafa@mail.com");
        String status = "STARTED";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/ride/5/start") //povuci voznju
        		.header("Authorization", "Bearer " + jwt))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(status));		
	}
	@Test
	public void startRide_rideNotPendingOrAccepted_returnsBadRequest() throws Exception {
		
        String jwt = jwtTokenUtil.generateToken("vlafa@mail.com");
               
        mockMvc.perform(MockMvcRequestBuilders.put("/api/ride/5/withdraw") //promeni stanje za voznju
        		.header("Authorization", "Bearer " + jwt))
                .andExpect(MockMvcResultMatchers.status().isOk())
        		.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("CANCELED")); //nije accepted ili pending
        
        mockMvc.perform(MockMvcRequestBuilders.put("/api/ride/5/start") //startuj voznju
        		.header("Authorization", "Bearer " + jwt))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	@Test
	public void startRide_invalidRideId_returnsNotFound() throws Exception {
		
        String jwt = jwtTokenUtil.generateToken("vlafa@mail.com");
                     
        mockMvc.perform(MockMvcRequestBuilders.put("/api/ride/123/start") //startuj voznju
        		.header("Authorization", "Bearer " + jwt))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	@Test
    public void startRide_withoutValidAuthorization_returnsForbidden() throws Exception {
		
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ride/5/start") //startuj voznju bez tokena
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
		
	}
}
